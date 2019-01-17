<%-- 
    Document   : reservation
    Created on : 25-nov-2018, 17.39.23
    Author     : salva
--%>

<%@page import="helpers.UserPower"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    if(UserPower.isAdmin(request.getSession()))
        return;
%>
<div>
    <ul class="nav nav-tabs" id="reservationTab" role="tablist">
        <li class="nav-item">
          <a class="nav-link active" id="reservation-tab" data-toggle="tab" href="#reservations" role="tab" aria-controls="reservations" aria-selected="true">Prenotabili</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" id="reserved-tab" data-toggle="tab" href="#reserved" role="tab" aria-controls="reserved" aria-selected="false">Prenotati</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" id="crono-tab" data-toggle="tab" href="#crono" role="tab" aria-controls="crono" aria-selected="false">Cronologia</a>
        </li>
    </ul>
    <div class="tab-content" id="reservationContent">
        <div class="tab-pane fade show active" id="reservations" role="tabpanel" aria-labelledby="reservation-tab">
            <table id="courses" class="table">
                <thead>
                  <tr>

                    <th scope="col">Nome Corso</th>
                    <th scope="col">Nome Insegnante</th>
                    <th scope="col">Data</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        <div class="tab-pane fade" id="reserved" role="tabpanel" aria-labelledby="reserved-tab">
            <table id="reservedCourses" class="table">
                <thead>
                  <tr>

                    <th scope="col">Nome Corso</th>
                    <th scope="col">Nome Insegnante</th>
                    <th scope="col">Data Corso</th>
                    <th scope="col">Data Prenotazione</th>
                  </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
        <div class="tab-pane fade" id="crono" role="tabpanel" aria-labelledby="crono-tab">
            <table id="cronoCourses" class="table">
                <thead>
                  <tr>

                    <th scope="col">Nome Corso</th>
                    <th scope="col">Nome Insegnante</th>
                    <th scope="col">Data Corso</th>
                    <th scope="col">Data Prenotazione</th>
                  </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
    
    
    
    
    
    
</div>

<script src="<%= request.getContextPath() %>/assets/js/reservation.js"></script>
