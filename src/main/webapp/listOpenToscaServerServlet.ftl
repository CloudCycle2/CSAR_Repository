<#import "layout.ftl" as layout>
<@layout.sb_admin>

<form action="${basePath}/createopentosca" method="POST" class="form-horizontal">
	<div class="form-group">
    	<label class="col-sm-2 control-label" for="serverName">Server Name</label>
    	<div class="col-sm-10">
    		<input type="text" class="form-control" id="serverName" name="serverName" placeholder="Server Name">
		</div>
	</div>
	<div class="form-group">
    	<label class="col-sm-2 control-label" for="serverUrl">ContainerAPI URL</label>
    	<div class="col-sm-10">
    		<input type="text" class="form-control" id="serverUrl" name="serverUrl" placeholder="http://localhost:1337/containerapi">
		</div>
	</div>
	<div class="form-group">
	    <div class="text-right col-sm-12">
	      <button type="submit" class="btn btn-success">
	      	<span class="glyphicon glyphicon-plus"></span> 
	      	&nbsp;Create new OpenTOSCA Server</button>
		</div>
	</div>
</form>

<table id="opentoscaserverlist" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Owner</th>
			<th>URL</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
<#list opentoscaservers as ot_server>
	<tr>
		<td><a href="${basePath}/opentoscaserver/${ot_server.id}">${ot_server.id}</a></td>
		<td><a href="${basePath}/opentoscaserver/${ot_server.id}">${ot_server.name}</a></td>
		<td><a href="#">${ot_server.user.name}</a></td>
		<td><a href="#">${ot_server.address}</a></td>
		<td><a href="${basePath}/deleteopentoscaserver/${ot_server.id}">Delete</a></td>
	</tr>
</#list>
</tbody>
</table>

<script>
${r"$(document).ready(function() {
    $('#opentoscaserverlist').dataTable();
} );"}
</script>

</@layout.sb_admin>