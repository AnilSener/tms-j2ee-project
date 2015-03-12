$(document).ready(function() {
	
$('select#countryID').change(function(event) {
	
        var countryID = $("select#countryID").val();
        $.getJSON('getAllCitiesByCountryID', {
        	countryID : countryID,
        	type:"application/json"
        	}, function(response) {

        var select = $('#cityID');
        select.find('option').remove();
          $.each(response, function(index, value) {
        	  
          $('<option>').val(value.id).text(value.name).appendTo(select);
      });
        });
        $.getJSON('getCountryDataByIDServlet', {
        	countryID : countryID,
        	type:"application/json"
        	}, function(response) {
        		 $.each(response, function(index, value) {
        	$("#currency").val(value.currency);
        	
        		 });
        	});
			
        });
});