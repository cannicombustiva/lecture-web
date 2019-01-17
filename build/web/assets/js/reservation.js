/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//function () {}
//() => {}

(function(){
    const getUserReservations = (date) =>
        fetch(window.MAIN_PATH + '/Api/Reservations?userId=' + window.USER_ID + '&date=' + date)
            .then(res => res.json())
            .then(apiResponse => apiResponse.items) //ritorna la promise con l'array items
    
    const getUserReservationsBookable = () => 
        fetch(window.MAIN_PATH + '/Api/Reservations/Bookable?userId=' + window.USER_ID)
            .then(res => res.json())
            .then(apiResponse => apiResponse.items)
    
    const getUserReservationsHistory = () =>
        fetch(window.MAIN_PATH + '/Api/Reservations/History?userId=' + window.USER_ID)
            .then(res => res.json())
            .then(apiResponse => apiResponse.items)

     const getCourseTeacher = () =>
        fetch(window.MAIN_PATH + '/Api/CourseTeacher')
            .then(res => res.json());
    
    const saveReservation = data =>
        fetch(window.MAIN_PATH + '/Api/Reservations', {
            headers: {
                'Content-Type': 'application/json'
            },
            method: 'POST',
            body: JSON.stringify(data)
        })
    
    const formatDate = date => {
        const isoDate = date.toISOString().split('.');
        return isoDate[0];
    }
        
    const bookablePage = () => {
        //definisco printItems che non partirà, infatti parte il Promise.all
        const printItems = (items) => {
            const trItems = items.reduce((acc, item) => {
                return acc.concat(`
                        <tr>
                            <td>${item.courseName}</td>
                            <td>${item.teacherName}</td>
                            <td>${item.date}</td>
                            <td><button class="btn btn-success" onClick="window.onReserve(this,${item.id})">Prenota</button></td>
                        </tr>
                    `);
            }, '');
            document.querySelector("#courses tbody").innerHTML = trItems;
        }
        
         getUserReservationsBookable()
            .then(res => {
                printItems(res);
            })
        
        /*
        //la Promise.all prende un array di promise e restituisce UNA promise
        //con risultato un array dei risulati delle promise passate alla Promise.all
        Promise.all([getUserReservations(formatDate(new Date())), getCourseTeacher()])
            .then(res => {
                // Destructuring
                const [userReservations, coursesTeachers] = res; //res[0] è userReservation ...

                const bookableCourses = coursesTeachers.filter(courseTeacher => {
                    const bookedCourse = userReservations.find(reservation => reservation.courseTeacherId === courseTeacher.id);
                    //eslcudo dal filtro i corsi non prenotabili
                    return !Boolean(bookedCourse);
                });
                printItems(bookableCourses);
            })*/
    }   
    
    const reservedPage = () => {
        const printItems = (items) => {
            const trItems = items.reduce((acc, item) => {
                return acc.concat(`
                        <tr>
                            <td>${item.courseName}</td>
                            <td>${item.teacherName}</td>
                            <td>${item.courseTeacherDate}</td>
                            <!--<td><button class="btn btn-success" onClick="window.onReserve(this,${item.id})">Prenota</button></td>-->
                            <td>${item.date}</td>
                        </tr>
                    `);
            }, '');
            document.querySelector("#reserved tbody").innerHTML = trItems;
        }
        
        getUserReservations(formatDate(new Date()))
            .then(res => {
                printItems(res);
            })
    }  
    
     const historyPage = () => {
        const printItems = (items) => {
            const trItems = items.reduce((acc, item) => {
                return acc.concat(`
                        <tr>
                            <td>${item.courseName}</td>
                            <td>${item.teacherName}</td>
                            <td>${item.courseTeacherDate}</td>
                            <td>${item.date}</td>
                        </tr>
                    `);
            }, '');
            document.querySelector("#crono tbody").innerHTML = trItems;
        }
        
        getUserReservationsHistory()
            .then(res => {
                printItems(res);
            })
    }  
    
    const tabHandler = () => {
        const reservationTab = document.querySelector('#reservationTab');
        reservationTab.addEventListener('click', event => {
            const {id} = event.target;
            const type = id.split('-').slice(0,1)[0];
            console.log(type);
            switch(type) {
                case 'reservation':
                    bookablePage();
                    break;
                case 'reserved':
                    reservedPage();
                    break;
                case 'crono':
                    historyPage();
                    break;
                default:
                    break;
            }
        })
    }
    
    const onReserve = (button, courseTeacherId) =>
        saveReservation({
            courseTeacherId,
            userId: window.USER_ID
        })
        .then(() => {
            const successElement = document.createElement('div');
            successElement.innerHTML = 'Prenotata';
            const container = button.parentNode;
            container.insertBefore(successElement, button);
            container.removeChild(button);
            
        })
        .catch(error => console.log(error)) 
        
    
    
    tabHandler();
    bookablePage();
    window.onReserve = onReserve;
})();

