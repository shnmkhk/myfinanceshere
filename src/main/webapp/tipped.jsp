<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<!--[if lt IE 9]>
  <script type="text/javascript" src="/js/excanvas/excanvas.js"></script>
<![endif]-->
<script type="text/javascript" src="/scripts/spinners/spinners.min.js"></script>
<script type="text/javascript" src="/scripts/tipped/tipped.js"></script>

<link rel="stylesheet" type="text/css" href="css/tipped/tipped.css" />

<div style="width: 10%;">
	<a href="http://www.google.com" id="demo_ajax">Click</a>
</div>
<script type="text/javascript">
	jQuery(document).ready(function() {
		Tipped.create("#demo_ajax", "private/popup.jsp", {
			ajax : true
		});
	});
</script>