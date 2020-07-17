function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}
// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;
var successCount =0;
var brandName="";
var categoryName="";

function processData(){
	
	console.log("processdata");
	$('#process-data').prop('disabled',true);
	var file = $('#brandFile')[0].files[0];
	processCount = 0;
	successCount=0;
	fileData = [];
	errorData = [];
	readFileData(file, readFileDataCallback);
	

}

function readFileDataCallback(results){
	fileData = results.data;
	console.log("readFileDataCallback");
	console.log("the length of  the file is");
	console.log(fileData.length);
	if(fileData.length>5000){
		alert("row limit is 5000");
		return ;
	}

	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	
	//If everything processed then return
	if(processCount==fileData.length){
		getBrandList();
		var $file = $('#brandFile');
			$file.val('');
			$('#brandFileName').html("Choose File");
		if(errorData.length>0){
			$("#download-errors").prop('disabled', false);
		}
		else{
			$("#download-errors").prop('disabled', true);
			
		}

		return;
		
	}
	
	//Process next row
	var row = fileData[processCount];
	processCount++;
	
	var json = JSON.stringify(row);
	var url = getBrandUrl();

	//Make ajax call
	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			successCount++;
			console.log("success count");
			console.log(successCount);
			uploadRows();  
		},
		error: function(response){
			row.error=response.responseText
			errorData.push(row);
			uploadRows();
		}
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

function getBrandList(){

	var url = getBrandUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			displayBrandList(data);  
		},
		error: errorPopUp
	});
}

function errorPopUp(response){
	var response = JSON.parse(response.responseText);
	$("#error-message").text(response.message);
	$('#error-alert').modal('toggle');
	setTimeout(function() {
	    $('#error-alert').modal('toggle');
	}, 3000);
	
	
}

function displayBrandList(data){
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button class="btn btn-dark btn-sm" onclick="displayEditBrand(' + e.id + ')">Edit</button>';
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
		$tbody.append(row);
	}
}

function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);	
	$("#brand-edit-form input[name=category]").val(data.category);	
	$("#brand-edit-form input[name=id]").val(data.id);	
	$("#update-brand").prop('disabled', true);
	brandName=data.brand;
	categoryName=data.category;
	$('#edit-brand-modal').modal('toggle');
}

function displayEditBrand(id){
	var url = getBrandUrl() + "/" + id;
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			displayBrand(data);   
		},
		error: handleAjaxError
	});	
}


function updateBrand(event){
	
	//Get the ID
	var id = $("#brand-edit-form input[name=id]").val();	
	var url = getBrandUrl() + "/" + id;

	//Set the values to update
	var $form = $("#brand-edit-form");
	console.log("update brand");
	console.log($form);
	var json = toJson($form);

	$.ajax({
		url: url,
		type: 'PUT',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			$('#edit-brand-modal').modal('toggle');
			successPopup("Brand is updated");
			getBrandList();   
		},
		error: handleAjaxError
	});
	
	return false;
}

function addBrand(){
	//Set the values to update
	

	
	var $form = $("#brand-add-form");

	var json = toJson($form);
	var url = getBrandUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},	   
		success: function(response) {
			$('#add-brand-modal').modal('toggle');
			successPopup("Brand is added");
			getBrandList();  
		},
		error: handleAjaxError
	});
	
	return false;
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	successCount=0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#successCount').html("" + successCount);
	$("#errorCount").css("color", "black");
	if(errorData.length>0){
		$("#errorCount").css("color", "red");
	}

	$('#errorCount').html("" + errorData.length);
}

function displayUploadData(){
	resetUploadDialog(); 
	$("#download-errors").prop('disabled', true);
	$("#process-data").prop('disabled', true);
	$('#upload-brand-modal').modal('toggle');
}

function updateFileName(){
	var $file = $('#brandFile');
	var fileName = $file.val();
	$('#brandFileName').html(fileName);
	$('#process-data').prop('disabled',false);
	$("#download-errors").prop('disabled', true);
	processCount = 0;
	successCount=0;
	fileData = [];
	errorData = [];
	$('#rowCount').html("" + fileData.length);
	$('#successCount').html("" + successCount);
	$("#errorCount").css("color", "black");
$('#errorCount').html("" + errorData.length);
}

function displayAddModal(){
	 $("#add-brand").prop('disabled', true);
	 $( '#brand-add-form' ).each(function(){
   			 this.reset();
				});
	$('#add-brand-modal').modal('toggle');
}

function init(){
	$('#add-brand').click(addBrand);
	$('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
	$('#add-data').click(displayAddModal);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
	$('#brandFile').on('change', updateFileName);

}

$(document).ready(init);
$(document).ready(getBrandList);



$('#brand-edit-form input[name=brand],#brand-edit-form input[name=category] ').on("input",function(){
  if (($('#brand-edit-form input[name=brand]').val().length>0 && $('#brand-edit-form input[name=brand]').val()!=brandName) ||
  	($('#brand-edit-form input[name=category]').val().length>0 && $('#brand-edit-form input[name=category]').val()!=categoryName) ){
    $("#update-brand").prop('disabled', false);
  }else {
        $("#update-brand").prop("disabled", true);
    }
});

$('#brand-add-form input[name=brand],#brand-add-form input[name=category] ').on("input",function(){
  
    if ($('#brand-add-form input[name=brand]').val().length   >   0   &&
        $('#brand-add-form input[name=category] ').val().length  >   0  ) {
        $("#add-brand").prop("disabled", false);
    }
    else {
        $("#add-brand").prop("disabled", true);
    }
  
});