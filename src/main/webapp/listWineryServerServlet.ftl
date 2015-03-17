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
            <li><a href="#create" data-toggle="tab">Create Winery Server</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
        	<div class="tab-pane fade" id="create" style="padding-top: 20px;">
				<form action="${basePath}/createwinery" method="POST" class="form-horizontal">
					<div class="form-group">
				    	<label for="inputWineryServerName" class="col-sm-2 control-label">Name</label>
				    	<div class="col-sm-10">
				      		<input name="wineryServerName" type="text" class="form-control" id="inputWineryServerName" placeholder="Server Name">
				    	</div>
				  	</div>
				  	<div class="form-group">
				    	<label for="inputWineryServerUrl" class="col-sm-2 control-label">URL</label>
				    	<div class="col-sm-10">
				      		<input name="wineryServerUrl" type="text" class="form-control" id="inputWineryServerUrl" placeholder="http://localhost:1337/winery">
				    	</div>
				  	</div>
				  	<button type="submit" class="btn btn-success pull-right">
			      		<span class="glyphicon glyphicon-plus"></span>
			      		Create new winery server
			      	</button>
				</form>		            	
            </div>
        </div>
    </div>
    <!-- /.panel-body -->
</div>

<div class="row" style="margin-bottom: 20px;">
	<div class="col-lg-12">
		<h2>Created Winery Servers</h2>
		<#if wineryServers?size gt 0>	
			<table id="wineryServerList" class="table table-striped table-bordered" border="1">
				<thead>
					<tr>
					    <th>ID</th>
						<th>Name</th>
						<th>URL</th>
					</tr>
				</thead>
				<tbody>
			<#list wineryServers as winery>
				<tr>
				    <td>${winery.id}</td>
					<td><a href="${basePath}/wineryserver/${winery.id}">${winery.name}</a></td>
					<td><a href="${winery.address}" target="_blank">${winery.address}</a></td>
				</tr>
			</#list>
			</tbody>
			</table>
			
			<script>
					window.onload=function(){
						$(document).ready(function() {
					    	$('#wineryServerList').dataTable();
						});
					}
			</script>
		<#else>
			<div class="alert alert-warning" role="alert">No Winery Servers created</div>
		</#if>
	</div>
</div>

</@layout.sb_admin>