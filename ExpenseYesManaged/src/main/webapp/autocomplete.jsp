<head>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
	<script type="text/javascript" src="<c:url value='/scripts/jquery-1.10.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/jquery-ui.js'/>"></script>
	 <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<div class="ui-widget">
		<label for="tags">Tags: </label> <input id="tags" />
	</div>
	<script type="text/javascript">
		$(function() {
			console.log("Applying autocomplete");
			var availableTags = [ "ActionScript", "AppleScript", "Asp",
					"BASIC", "C", "C++", "Clojure", "COBOL", "ColdFusion",
					"Erlang", "Fortran", "Groovy", "Haskell", "Java",
					"JavaScript", "Lisp", "Perl", "PHP", "Python", "Ruby",
					"Scala", "Scheme" ];
			$("#tags").autocomplete({
				source : availableTags
			});
		});
	</script>
</body>
