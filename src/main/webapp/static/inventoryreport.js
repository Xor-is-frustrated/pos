
function getUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory/report";
}


function getReportList(){
	var url = getUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			displayEmployeeList(data);  
		},
		error: function() {
			console.log("not okk");
		},
	});
}


function displayEmployeeList(data){
	var $tbody = $('#inventoryreport-table').find('tbody');
	$tbody.empty();
	console.log(data);
	for(var i in data){
		var e = data[i];
		
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>'  + e.quantity + '</td>'
		
		
		+ '</tr>';
		$tbody.append(row);
	}
}


$(document).ready(getReportList);








