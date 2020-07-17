
function getUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderitem/report";
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
		+ '<td>' + e.category + '</td>'
		+ '<td>'  + e.revenue.toFixed(2) + '</td>'
		+ '</tr>';
		$tbody.append(row);
	}
}

function setCategoryDropDown(applyDropdown){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	var url=baseUrl+"/api/brand/distinctcategories";

	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			console.log("setCategoryDropDown");
			console.log(data);
			displayCategoryDropDown(data);  
			applyDropdown();
		},
		error: handleAjaxError
	});
}

function setBrandDropDown(setCategoryDropDown,applyDropdown){
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	var url=baseUrl+"/api/brand/distinctbrands";

	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			console.log("setBrandDropDown");
			console.log(data);
			displayBrandDropDown(data);  
			setCategoryDropDown(applyDropdown);
		},
		error: handleAjaxError
	});
}

function displayCategoryDropDown(data){
	var $select = $('#category-dropdown');
	console.log("category");
	console.log(data);

	for(var i in data)
	{
		var e=data[i];
		var row = '<option value="'+e+'">'+e+'</option>';
		$select.append(row);
		
	}

	var e='.';
	var row = '<option type="hidden" value="'+e+'">'+e+'</option>';
	$select.multiselect('rebuild'); 
	$("#category-dropdown").multiselect('selectAll', false);
	$("#category-dropdown").multiselect('updateButtonText');
}

function displayBrandDropDown(data){
	var $select = $('#brand-dropdown');
	console.log("brand");
	console.log(data);
	for(var i in data)
	{
		var e=data[i];
		var row = '<option value="'+e+'">'+e+'</option>';
		$select.append(row);
		
	}
	var e='.';
	var row = '<option type="hidden" value="'+e+'">'+e+'</option>';
	$select.multiselect('rebuild'); 
	$("#brand-dropdown").multiselect('selectAll', false);
	$("#brand-dropdown").multiselect('updateButtonText');

	
}

function setDropDown(){

	$('#category-dropdown').multiselect({
		nonSelectedText: 'Select category',
		enableFiltering: true,
		includeSelectAllOption: true,
		enableCaseInsensitiveFiltering: true,
		buttonWidth:'400px'
	});


	$('#brand-dropdown').multiselect({
		nonSelectedText: 'Select brand',
		enableFiltering: true,
		includeSelectAllOption: true,
		enableCaseInsensitiveFiltering: true,
		buttonWidth:'400px'
	});
	setBrandDropDown(setCategoryDropDown,applyDropdown);
	
	
}

function toJsonArrays($form){
	var serialized = $form.serializeArray();
	console.log(serialized);

	var s = '';
	var data = {};
	var data1=[];
	
	for(s in serialized){
		data1.push(serialized[s]['value']);
		
		
	}

	data[serialized[s]['name']]=data1;
	return data;

}

function toJsonDate($form){
	var serialized = $form.serializeArray();
	
	var dateobj = new Date(serialized[0]['value']); 
	if(serialized[0]['name']=='endDate'){
  		dateobj.setDate(dateobj.getDate() + 1);
  	}
	
   var date = dateobj.toISOString(); 
   console.log("jsondate");
   console.log(dateobj);
	var s = '';
	var data = {};
	for(s in serialized){
		data[serialized[s]['name']] = date;
	}
	return data;
}

function check($form){
	var serialized = $form.serializeArray();
	if(Array.isArray(serialized) && serialized.length)
	{
		return true;
	}
	return false;
}
function applyDropdown(){
	$form = $("#category_form");
	console.log("apply dropdown category_form");
	console.log($form);
	if(check($form)==false){
		 $("#error-message").text("please select atleast one category");
    	$('#error-alert').modal('toggle');
		return false;	
	}
	var json = toJsonArrays($form);

	$form1 = $("#brand_form");
		
	if(check($form1)==false){
		 $("#error-message").text("please select atleast one category");
    	$('#error-alert').modal('toggle');
		return false;	

	}
	var json1 = toJsonArrays($form1);
	$form2=$("#startdate_form");
	var json2 = toJsonDate($form2);

	$form3=$("#enddate_form");
	var json3 = toJsonDate($form3);

	json = $.extend(json, json1);
	json = $.extend(json, json2);
	json = $.extend(json, json3);
	json = JSON.stringify(json);
	
	console.log("final");
	console.log(json);

	var url = getUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			displayEmployeeList(response);  
		},
		error: handleAjaxError
	});

	return false;
	

}

function init(){
	$('#apply-dropdown').click(applyDropdown);
	
	
}

function setMaxDate(){
	startdate_dropdown.max = new Date().toISOString().split("T")[0];
	enddate_dropdown.max = new Date().toISOString().split("T")[0];
	$(document).ready( function() {
    var now = new Date();
    var day = ("0" + now.getDate()).slice(-2);
    var month = ("0" + (now.getMonth() + 1)).slice(-2);
    var today = now.getFullYear()+"-"+(month)+"-"+(day) ;
   $('#enddate_dropdown').val(today);
});
}

$(document).ready(init);
$(document).ready(setDropDown);
$(document).ready(setMaxDate);








