/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import Models.Course;
import Models.CourseTeacher;
import Models.Reservation;
import Models.Session;
import Models.Teacher;
import Models.User;
import helpers.CustomExceptions.NoTokenException;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import helpers.CustomExceptions.NoUserException;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author salva
 */
public class DB {

    private static final String URL = "jdbc:mysql://localhost:3306/lectures"; // url del mioDB
    private static final String USER = "root"; // login utente da usare per connettersi
    private static final String PWD = ""; // password utente
    private Connection conn;

    public DB() throws SQLException {
        registerDriver();
        conn = DriverManager.getConnection(URL, USER, PWD);
    }

    // metodo per registrare il Driver JDBC da usare durante le operazioni
    // di interazione con il DB.
    private static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());//in quello della prof è diverso
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<CourseTeacher> getAllValidCoursesTeacher() throws SQLException {

        Statement st = conn.createStatement();

        List<CourseTeacher> out = new ArrayList();

        ResultSet rs = st.executeQuery("SELECT courses_teachers.id AS \"id\", teachers.name AS \"teacher\", courses_teachers.teacher_id AS \"teacherId\", courses_teachers.course_id AS \"courseId\", courses.name AS \"course\", courses_teachers.date AS \"date\"  FROM courses\n"
                + "JOIN courses_teachers ON(courses.id=courses_teachers.course_id)\n"
                + "JOIN teachers ON(teachers.id=courses_teachers.teacher_id)\n"
                + "WHERE courses_teachers.date >= NOW()");
        while (rs.next()) {
            String course = rs.getString("course");
            String teacher = rs.getString("teacher");
            String date = rs.getString("date");
            int courseId = rs.getInt("courseId");
            int teacherId = rs.getInt("teacherId");
            int id = rs.getInt("id");

            CourseTeacher newCourse = new CourseTeacher(id, course, teacher, date, teacherId, courseId);
            out.add(newCourse);
        }

        return out;
    }

    public List<CourseTeacher> getAllCoursesTeacher() throws SQLException {

        Statement st = conn.createStatement();

        List<CourseTeacher> out = new ArrayList();

        ResultSet rs = st.executeQuery("SELECT courses_teachers.id AS \"id\", teachers.name AS \"teacher\", courses_teachers.teacher_id AS \"teacherId\", courses_teachers.course_id AS \"courseId\", courses.name AS \"course\", courses_teachers.date AS \"date\"  FROM courses\n"
                + "JOIN courses_teachers ON(courses.id=courses_teachers.course_id)\n"
                + "JOIN teachers ON(teachers.id=courses_teachers.teacher_id)");
        while (rs.next()) {
            String course = rs.getString("course");
            String teacher = rs.getString("teacher");
            String date = rs.getString("date");
            int courseId = rs.getInt("courseId");
            int teacherId = rs.getInt("teacherId");
            int id = rs.getInt("id");

            CourseTeacher newCourse = new CourseTeacher(id, course, teacher, date, teacherId, courseId);
            out.add(newCourse);
        }

        return out;
    }

    public List<Teacher> getAllTeachers() throws SQLException {

        Statement st = conn.createStatement();

        List<Teacher> out = new ArrayList();

        ResultSet rs = st.executeQuery("SELECT * FROM `teachers`");
        while (rs.next()) {
            String name = rs.getString("name");
            int id = rs.getInt("id");
            Teacher newTeacher = new Teacher(name, id);
            out.add(newTeacher);
        }

        return out;
    }

    public List<Course> getAllCourses() throws SQLException {

        Statement st = conn.createStatement();

        List<Course> out = new ArrayList();

        ResultSet rs = st.executeQuery("SELECT * FROM `courses`");
        while (rs.next()) {
            String name = rs.getString("name");
            int id = rs.getInt("id");
            Course newCourse = new Course(name, id);
            out.add(newCourse);
        }

        return out;
    }

    public User getUserByEmail(String email) throws SQLException, NoUserException {

        PreparedStatement st = conn.prepareStatement("SELECT * from users WHERE email = ?");
        st.setString(1, email);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) {
            throw new NoUserException("Email non valida");
        }

        String password = rs.getString("password");
        int id = rs.getInt("id");
        int roleId = rs.getInt("role_id");
        User user = new User(email, password, id, roleId);
        return user;

    }

    public User getUserById(int id) throws SQLException, NoUserException {

        PreparedStatement st = conn.prepareStatement("SELECT users.email AS \"email\", users.role_id AS \"roleId\" , roles.name  AS \"roleName\" FROM users\n"
                + "JOIN roles ON(users.role_id=roles.id)\n"
                + "WHERE users.id = ?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) {
            throw new NoUserException("Email non valida");
        }

        String email = rs.getString("email");
        int roleId = rs.getInt("roleId");
        String roleName = rs.getString("roleName");
        User user = new User(id, email, roleId, roleName);
        return user;

    }

    public void insertCourse(Course course) throws SQLException {
        // Query per inserire
        PreparedStatement st = conn.prepareStatement("INSERT INTO `courses` (`name`) VALUES (?)");

        st.setString(1, course.name);
        st.executeUpdate();
    }

    public void deleteCourse(int courseId) throws SQLException {

        PreparedStatement st = conn.prepareStatement("DELETE FROM `courses` WHERE `id` = (?)");

        st.setInt(1, courseId);
        st.executeUpdate();
    }

    public void insertSession(Session session) throws SQLException {
        // Query per inserire
        PreparedStatement st = conn.prepareStatement("INSERT INTO `sessions` (`user_id`,`token`) VALUES (?,?)");

        st.setInt(1, session.userId);
        st.setString(2, session.token);
        st.executeUpdate();
    }

    public void insertTeacher(Teacher teacher) throws SQLException {
        // Query per inserire
        PreparedStatement st = conn.prepareStatement("INSERT INTO `teachers` (`name`) VALUES (?)");

        st.setString(1, teacher.name);
        st.executeUpdate();
    }

    public void insertReservation(Reservation reservation) throws SQLException {
        // Query per inserire
        PreparedStatement st = conn.prepareStatement("INSERT INTO `reservation` (`course_teacher_id`,`user_id` ) VALUES (?,?)");

        st.setInt(1, reservation.courseTeacherId);
        st.setInt(2, reservation.userId);
        st.executeUpdate();
    }

    public void deleteTeacher(int teacherId) throws SQLException {

        PreparedStatement st = conn.prepareStatement("DELETE FROM `teachers` WHERE `id` = (?)");

        st.setInt(1, teacherId);
        st.executeUpdate();
    }

    public void deleteCourseTeacher(int id) throws SQLException {

        PreparedStatement st = conn.prepareStatement("DELETE FROM `courses_teachers` WHERE `id` = (?)");

        st.setInt(1, id);
        st.executeUpdate();
    }

    public void insertCourseTeacher(CourseTeacher courseTeacher) throws SQLException, ParseException {
        // Query per inserire
        PreparedStatement st = conn.prepareStatement("INSERT INTO `courses_teachers` (`course_id`, `teacher_id`, `date` ) VALUES (?, ?, ?)");
        // Metodo per parsare la data inserita dall'admin, per collegare corso all'insegnante
        // Perché mi dava un errore di tipo per il fomrato della data non accettabile
        Date newDate = new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(courseTeacher.date);

        st.setInt(1, courseTeacher.courseId);
        st.setInt(2, courseTeacher.teacherId);
        st.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm").format(newDate));
        st.executeUpdate();
    }

    public Session getSession(String token) throws SQLException, NoTokenException {
        PreparedStatement st = conn.prepareStatement("SELECT * from sessions WHERE token = ?");
        st.setString(1, token);
        ResultSet rs = st.executeQuery();

        if (!rs.next()) {
            throw new NoTokenException();
        }

        String date = rs.getString("insertion_date");
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        Session session = new Session(userId, token, id, date);
        return session;
    }

    public int getUserRolePower(int roleId) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT * from roles WHERE id = ?");
        st.setInt(1, roleId);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getInt("power");
    }

    public List<Reservation> getUserReservations(int userId, Date dateParam) throws SQLException {

        PreparedStatement st = conn.prepareStatement("SELECT reservation.id AS \"id\", reservation.date  AS \"date\", reservation.course_teacher_id  AS \"course_teacher_id\", teachers.name AS  \"teacher_name\", courses.name AS \"course_name\", courses_teachers.date AS \"course_date\"  FROM reservation\n"
                + "JOIN courses_teachers ON(reservation.course_teacher_id=courses_teachers.id)\n"
                + "JOIN teachers ON(teachers.id=courses_teachers.teacher_id)\n"
                + "JOIN courses ON(courses.id=courses_teachers.course_id) and reservation.user_id = ?\n"
                + "WHERE courses_teachers.date > ?");

        java.sql.Date sqlDate = new java.sql.Date(dateParam.getTime());
        st.setInt(1, userId);
        st.setDate(2, sqlDate);
        ResultSet rs = st.executeQuery();
        List<Reservation> out = new ArrayList();
        while (rs.next()) {
            int courseTeacherId = rs.getInt("course_teacher_id");
            int id = rs.getInt("id");
            String courseName = rs.getString("course_name");
            String teacherName = rs.getString("teacher_name");
            String courseDate = rs.getString("course_date");
            String date = rs.getString("date");
            Reservation newReservation = new Reservation(id, userId, courseTeacherId, courseName, teacherName, courseDate, date);
            out.add(newReservation);
        }
        return out;

    }

    public List<Reservation> getUserReservationsHistory(int userId) throws SQLException {

        PreparedStatement st = conn.prepareStatement("SELECT reservation.id AS \"id\", reservation.date  AS \"date\", reservation.course_teacher_id  AS \"course_teacher_id\", teachers.name AS  \"teacher_name\", courses.name AS \"course_name\", courses_teachers.date AS \"course_date\"  FROM reservation\n"
                + "JOIN courses_teachers ON(reservation.course_teacher_id=courses_teachers.id)\n"
                + "JOIN teachers ON(teachers.id=courses_teachers.teacher_id)\n"
                + "JOIN courses ON(courses.id=courses_teachers.course_id) and reservation.user_id = ?\n"
                + "WHERE courses_teachers.date < NOW()");

        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();
        List<Reservation> out = new ArrayList();
        while (rs.next()) {
            int courseTeacherId = rs.getInt("course_teacher_id");
            int id = rs.getInt("id");
            String courseName = rs.getString("course_name");
            String teacherName = rs.getString("teacher_name");
            String courseDate = rs.getString("course_date");
            String date = rs.getString("date");
            Reservation newReservation = new Reservation(id, userId, courseTeacherId, courseName, teacherName, courseDate, date);
            out.add(newReservation);
        }
        return out;

    }

    public String getRoleName(int roleId) throws SQLException {
        PreparedStatement st = conn.prepareStatement("SELECT name from roles WHERE id = ?");
        st.setInt(1, roleId);
        ResultSet rs = st.executeQuery();
        rs.next();
        return rs.getString("name");
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public boolean isLogged(HttpSession session, HttpServletRequest req) throws SQLException, ParseException {
        int maxInterval = 30;
        String token = "";
        String appToken = req.getHeader("Authorization");
        if (session == null && appToken == null) {
            return false;
        }

        if (appToken != null && !appToken.isEmpty()) {
            token = appToken;
        } else {
            token = (String) session.getAttribute("token");
            maxInterval = session.getMaxInactiveInterval();
        }

        if (token == null) {
            return false;
        }

        try {
            Session dbSession = getSession(token);
            Date newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dbSession.date);
            long diffdate = getDateDiff(newDate, new Date(), TimeUnit.MINUTES);
            if (diffdate <= maxInterval) {
                return true;
            }

        } catch (NoTokenException ex) {
            return false;
        }
        return false;
    }

}
