<h2>Stylist Reservation System</h2>

<h3> Swagger UI</h3>
http://localhost:8080/swagger-ui.html#/

<hr>

<h3>Important Implementation Notes</h3>

<ul>
<li><b> This implementation not fully covers all features but most of them works well. </b></li>
<li><b> Time input parameters not built as LocalDateTime or and other Datetime formats, For testing purpose, you should easily set time for 9AM = 900, 10AM = 1000, 1030, 1100 etc. </b></li>
<li><b> Time input paramteres configurable inside of the application.properties and service layer validates time input according to timeSlotFormats. </b></li>
<li><b> The system checks the input time greater and equal with the time of free stylist if its matches, system send you an advice about possible appointment time. </b></li>
<li><b> Appointment service is the main service which is responsible to manage logic. </b></li>
</ul>


<h3> What is missing and should be implemented on next release and improvements</h3>

<ul>
<li> More proper input type for times. This need to be replaced with LocalDateTime and timeslots for 30 minutes</li>
<li> Stylist update not available should be added</li>
</ul>