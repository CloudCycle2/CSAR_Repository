<#import "layout.ftl" as layout>
<@layout.sb_admin>

<form action="${basePath}/createopentosca" method="POST" class="form-horizontal">
	<div class="form-group">
    	<label class="col-sm-2 control-label" for="serverName">Name</label>
    	<div class="col-sm-10">
    		<input type="text" class="form-control" id="serverName" name="serverName" placeholder="Server Name">
		</div>
	</div>
	<div class="form-group">
    	<label class="col-sm-2 control-label" for="serverUrl">URL</label>
    	<div class="col-sm-10">
    		<input type="text" class="form-control" id="serverUrl" name="serverUrl" placeholder="http://localhost:1337/containerapi">
		</div>
	</div>
	<div class="form-group">
	    <div class="text-left col-sm-12">
	      <button type="submit" class="btn btn-success">
	      	<span class="glyphicon glyphicon-plus"></span> 
	      	&nbsp;Create new OpenTOSCA Server</button>
		</div>
	</div>
</form>

<table id="openToscaServerList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>URL</th>
		</tr>
	</thead>
	<tbody>
<#list opentoscaservers as ot_server>
	<tr>
		<td>${ot_server.id}</td>
		<td><a href="${basePath}/opentoscaserver/${ot_server.id}">${ot_server.name}</a></td>
		<td>${ot_server.address}</td>
	</tr>
</#list>
</tbody>
</table>

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#openToscaServerList').dataTable();
		});
	}
</script>

</@layout.sb_admin>