---
title: Kırklareli Üniversitesi Yemek Takvimi
permalink: takvim/
---

<script src="https://code.jquery.com/jquery-1.11.3.js"></script>

<style>
body, html {
	height: 100%;
	width: 100%;
	padding: 0;
	margin: 0;
}

.container-lg {
	max-width: unset !important;
}

table#table {
    border-collapse: collapse;
	width: 100%;
}

tbody#tbody {
    border-collapse: collapse;
    border: 1px solid;
}
tr:last-child {
	min-width: 22vw !important;
}

th {
    border: 1px solid;
	height: 48px;
    text-align: left;
    padding: 1%;
}

td {
    border: 1px solid;
	height: 48px;
    min-width: 20vw;
    text-align: left;
    padding: 1%;
    white-space: nowrap;
}

tr:nth-child(2n+1) {
    background: #0003;
}
</style>

<div id="table_container">
	<table id="table">
		<tbody id="tbody">
		
		</tbody>
	</table>

</div>

<script>
	$("div h1:first-child").html("<a href='https://github.com/berkantkz/KLU_Yemek/'>KLU Yemek Takvimi</a><a href='https://github.com/berkantkz' style='float: right; font-size: 15pt;'>berkantkz</a>");
	
	$.getJSON("https://berkantkz.github.io/KLU_Yemek/list.json",function(item) {
		var asset = item[0];
		var content = '';
		content+='<tr>'
		content+='<th>Tarih</th>'
		content+='<th>Menu</th>'
		content+='</tr>'
		$.each(item, function(key, val) {
		
			var date = val.start.replace('00:00:00','') + ' ' + val.title;
			content+='<tr>'
			content+='<td>' + date + '</td>'
			content+='<td>' + val.aciklama + '</td>'
			content+='</tr>'
		});
  document.getElementById("tbody").innerHTML =  content;
  });
</script>