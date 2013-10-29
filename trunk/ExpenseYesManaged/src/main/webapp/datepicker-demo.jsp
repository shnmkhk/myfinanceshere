<!DOCTYPE html>

<html lang="en">
<head>
   <meta charset="utf-8">
   <title>Datepicker</title>
</head>
<body>

<input type="date" name="date-field" id="date-field" value="" size="20" />
<script src="/scripts/jquery-1.10.2.min.js"></script>
<script src="/scripts/jquery-ui.js"></script>
<script>
   (function() {
      var elem = document.createElement('input');
      elem.setAttribute('type', 'date');
      if (elem.type === 'text' ) {
    	    $('#date-field').datepicker({
    	        dateFormat: 'M d, yy',
    	        showWeek: true,
    	        firstDay: 1,
    	        changeMonth: true,
    	        changeYear: true
    	    });
      } 
   })();

</script>
</body>
</html>	
