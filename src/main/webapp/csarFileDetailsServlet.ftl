<#import "layout.ftl" as layout>
<@layout.sb_admin>

<div class="panel panel-default">
    <div class="panel-heading">
        Links
    </div>
    <!-- /.panel-heading -->
    <div class="panel-body">
        <div class="form-group">
            <div class="text-left col-sm-12">
                <a href="${basePath}/csar/${csar.id}">Back to CSAR ${csar.name}</a>
            </div>
            <div class="text-left col-sm-12">
            	<a href="${basePath}/downloadcsarfile?csarfileid=${csarFile.id}">Download ${csar.name} @ version ${csarFile.version}</a>
            </div>
        </div>
    </div>
    <!-- /.panel-body -->
</div>

<div class="panel panel-default">
    <div class="panel-heading">
        Operations
    </div>
    <!-- /.panel-heading -->
    <div class="panel-body">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs">
        	<li><a href="#delete" data-toggle="tab">Delete Version</a></li>
            <li><a href="#deploy" data-toggle="tab">Deploy on OpenTosca Server</a></li>
            <li><a href="#export" data-toggle="tab">Export to Winery Server</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div class="tab-pane fade" id="delete" style="padding-top: 20px;">
                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">
	        		<span class="glyphicon glyphicon-remove"></span>
	        		Delete
    			</button>
            </div>
            
        	<div class="tab-pane fade" id="deploy" style="padding-top: 20px;">
        		<#if allOpentoscaServers?size gt 0>
					<form action="${basePath}/deploycsarfile" method="POST" class="form-horizontal">
						<input type="hidden" name="csarfileId" value="${csarFile.id}" />
						<div class="form-group">
					  		<label class="col-sm-2 control-label" for="sel1">OpenTOSCA Server</label>
					  		<div class="col-sm-10">
					  			<select class="form-control" id="opentoscaId" name="opentoscaId">
									<#list allOpentoscaServers as otServer>
					    				<option value="${otServer.id}">${otServer.name} | ${otServer.address}</option>
									</#list>
					  			</select>
					  		</div>
						</div>
						
				      	<button type="submit" class="btn btn-success pull-right">
				      		<span class="glyphicon glyphicon-plus"></span> 
						    Deploy to OpenTOSCA Server
						</button>
					</form>
				<#else>
					<div class="alert alert-warning" role="alert">No OpenTOSCA Instances created</div>
				</#if>
        	</div>
        	
        	<div class="tab-pane fade" id="export" style="padding-top: 20px;">
        		<#if wineryServers?size gt 0>
					<form action="${basePath}/exporttowinery" method="POST" class="form-horizontal">
						<input type="hidden" name="csarfileId" value="${csarFile.id}" />
						<div class="form-group">
					  		<label class="col-sm-2 control-label" for="inputWineryId">Winery Server</label>
					  		<div class="col-sm-10">
					  			<select class="form-control" id="inputWineryId" name="wineryId">
									<#list wineryServers as ws>
						    			<option value="${ws.id}">${ws.name} | ${ws.address}</option>
									</#list>
					  			</select>
					  		</div>
						</div>
						      
						<button type="submit" class="btn btn-success pull-right">
							<span class="glyphicon glyphicon-share"></span> 
						   	Export to Winery
						</button>
					</form>
				<#else>
					<div class="alert alert-warning" role="alert">No Winery servers created</div>
				</#if>
        	</div>
        </div>
    </div>
    <!-- /.panel-body -->
</div> <!-- ./panel -->

<div class="row" style="margin-bottom: 20px;">
    <div class="col-lg-12">
		<h2>CsarFile Details</h2>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">CSAR:</div>
			<div class="col-md-9">${csar.name} (<a href="${basePath}/csar/${csar.id}">Details</a>)</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Version:</div>
			<div class="col-md-9">${csarFile.version}</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Filename:</div>
			<div class="col-md-9">${csarFile.name}</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Uploaded at:</div>
			<div class="col-md-9">${csarFile.uploadDate}</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Filesize:</div>
			<div class="col-md-9">${hashedFile.size} Byte</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Hash:</div>
			<div class="col-md-9">${hashedFile.hash}</div>
		</div>
	</div>
</div>

<div class="row" style="margin-bottom: 20px;">
    <div class="col-lg-12">
        <h2>Deployments</h2>
        <#if opentoscaDeployedTo?size gt 0>
	        <table id="otList" class="table table-striped table-bordered" border="1">
	            <thead>
	                <tr>
	                    <th>ID</th>
	                    <th>Name</th>
	                    <th>Address</th>
	                    <th>OpenTOSCA</th>
	                    <th>Location</th>
	                    <th>Undeploy</th>
	                </tr>
	            </thead>
	            <tbody>
	                <#list opentoscaDeployedTo as opentoscaServer>
	                    <tr>
	                        <td>${opentoscaServer.openToscaServer.id}</td>
	                        <td>${opentoscaServer.openToscaServer.name}</td>
	                        <td>${opentoscaServer.openToscaServer.address}</td>
	                        <td><a href="${basePath}/opentoscaserver/${opentoscaServer.getOpenToscaServer().getId()}">${opentoscaServer.openToscaServer.name}</a></td>
	                        <td>${opentoscaServer.location}</td>
	                        <td><a href="${basePath}/undeploycsarfile?opentoscaId=${opentoscaServer.openToscaServer.id}&csarfileId=${opentoscaServer.csarFile.id}"><i class="fa fa-eraser fa-lg"></i></a></td>
	                    </tr>
	                </#list>
	            </tbody>
	        </table>
		<#else>
			<div class="alert alert-warning" role="alert">No Deployments found</div>
		</#if>
    </div>
</div>   

<div class="row" style="margin-bottom: 20px;">
	<div class="col-lg-12">
		<h2>Cloud Instances</h2>
		<#if cloudInstances?size gt 0>
			<table id="ciList" class="table table-striped table-bordered" border="1">
				<thead>
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Address</th>
						<th>OpenTOSCA</th>
					</tr>
				</thead>
				<tbody>
					<#list cloudInstances as ci>
						<tr>
							<td>${ci.id}</td>
							<td>${ci.name}</td>
							<td>${ci.address}</td>
							<td><a href="${basePath}/#">${ci.openToscaServer.name}</a></td>
						</tr>
					</#list>
				</tbody>
			</table>
		<#else>
			<div class="alert alert-warning" role="alert">No Cloud Instances found</div>
		</#if>
	</div>
</div>	 

<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteModalLabel">Delete version of CSAR</h4>
            </div>
            <div class="modal-body">
                Do you really want to delete <strong>Version ${csarFile.version} of ${csar.name}</strong>?
            </div>
            <div class="modal-footer">
                <a href="${basePath}/deletecsarfile?csarfileid=${csarFile.id}">
	                <button type="button" class="btn btn-danger pull-right" style="margin: 3px;">
	                	Delete
	                </button>
                </a>
                <button type="button" class="btn btn-default pull-right" data-dismiss="modal" style="margin: 3px;">Cancel</button>
                <br />
                <br />
            </div>
        </div>
    </div>
</div>

<script>
	window.onload=function(){
		$(document).ready(function() {
	    	$('#otList').dataTable();
	    	$('#ciList').dataTable();
		});
	}
</script>
</@layout.sb_admin>