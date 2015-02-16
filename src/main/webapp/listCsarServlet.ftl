<#import "layout.ftl" as layout>
<@layout.sb_admin>

<form action="${basePath}/createcsar" method="POST" class="form-horizontal">
	<div class="form-group">
    	<label class="col-sm-2 control-label" for="serverName">Name</label>
    	<div class="col-sm-10">
    		<input type="text" class="form-control" id="csarName" name="csarName">
		</div>
	</div>
	<div class="form-group">
	    <div class="text-right col-sm-12">
	      <button type="submit" class="btn btn-success">
	      	<span class="glyphicon glyphicon-plus"></span> 
	      	&nbsp;Create new CSAR</button>
		</div>
	</div>
</form>

<table id="csarList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>CSAR Name</th>
			<th>Delete</th>
			<th>Edit</th>
		</tr>
	</thead>
	<tbody>
<#list csars as csar>
	<tr>
		<td><a href="${basePath}/csar/${csar.id}">${csar.name}</a></td>
		<td><a href="${basePath}/deletecsar/${csar.id}">Delete</a></td>
		<td><a onclick="editCsarName(${csar.id})">Edit</a></td>
	</tr>
</#list>
</tbody>
</table>

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#csarList').dataTable();
		});
	}
	
	function post(path, params, method) {
		method = method || "post";
		var form = document.createElement("form");
    	form.setAttribute("method", method);
    	form.setAttribute("action", path);

    	for(var key in params) {
        	if(params.hasOwnProperty(key)) {
            	var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
    	        hiddenField.setAttribute("name", key);
        	    hiddenField.setAttribute("value", params[key]);

            	form.appendChild(hiddenField);
        	}
    	}
    	document.body.appendChild(form);
    	form.submit();
	}
	
	function editCsarName(csarId) {
	    var editCsarPrompt = prompt("Please enter a name!");
	    if (editCsarPrompt != null) {
	        post("${basePath}" + "/editcsarname/", {csarid: csarId, name: editCsarPrompt});
	    }
	}
</script>

</@layout.sb_admin>