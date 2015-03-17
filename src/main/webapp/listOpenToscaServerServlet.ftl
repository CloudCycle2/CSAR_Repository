<#import "layout.ftl" as layout>
<@layout.sb_admin>

<div class="panel panel-default">
    <div class="panel-heading">
        Operations
    </div>
    <!-- /.panel-heading -->
    <div class="panel-body">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs">
            <li><a href="#create" data-toggle="tab">Create OpenTosca Server</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
        	<div class="tab-pane fade" id="create" style="padding-top: 20px;">
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
				      	<button type="submit" class="btn btn-success pull-right">
				      		<span class="glyphicon glyphicon-plus"></span> 
				      		Create new OpenTOSCA Server
				      	</button>
				</form>            	
            </div>
        </div>
    </div>
    <!-- /.panel-body -->
</div>


<div class="row" style="margin-bottom: 20px;">
	<div class="col-lg-12">
		<h2>Created OpenTosca Servers</h2>
		<#if opentoscaservers?size gt 0>
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
					<td><a href="${ot_server.address}" target="_blank">${ot_server.address}</a></td>
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
		<#else>
			<div class="alert alert-warning" role="alert">No OpenTosca Servers created</div>
		</#if>
	</div>
</div>

</@layout.sb_admin>