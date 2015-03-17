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
                <a href="${openToscaServer.getAddress()}">Visit Container-API</a><br/>
                <a href="http://${openToscaServer.getAddress().getHost()}:8080/vinothek">Visit Vinothek</a><br/>
                <a href="http://${openToscaServer.getAddress().getHost()}:8080/winery">Visit Winery</a>
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
            <li><a href="#edit" data-toggle="tab">Edit OpenTosca Server</a></li>
            <li><a href="#delete" data-toggle="tab">Delete OpenTosca Server</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
        	<div class="tab-pane fade" id="edit" style="padding-top: 20px;">
				<form action="${basePath}/updateopentoscaserver" method="POST" class="form-horizontal">
				  	<input type="hidden" name="openToscaServerId" value="${openToscaServer.id}"/>
				  	<div class="form-group">
		            	<label class="col-sm-2 control-label" for="nameField">Name</label>
		            	<div class="col-sm-10">
		                	<input type="text" class="form-control" id="nameField" name="openToscaServerName" value="${openToscaServer.name}" />
		                </div>
		          	</div>
		    
		        	<div class="form-group">
		        		<label class="col-sm-2 control-label" for="urlField">ContainerAPI URL</label>
		        		<div class="col-sm-10">
		                	<input type="url" class="form-control" id="urlField" name="address" value="${openToscaServer.address}" />
		        		</div>
		        	</div>
		        	
				    <button type="submit" class="btn btn-success pull-right" />
				    	<span class="glyphicon glyphicon-ok"></span> 
				    	Update OpenTOSCA Server
				    </button>
				</form>            	
            </div>
            
            <div class="tab-pane fade" id="delete" style="padding-top: 20px;">
            	<button tabindex="-1" id="deleteBtn" type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal">
		    		<span class="glyphicon glyphicon-remove"></span>
		    		Delete
				</button>
			</div>
        </div>
    </div>
    <!-- /.panel-body -->
</div>

<div class="row" style="margin-bottom: 20px;">
	<div class="col-lg-12">    	
		<h2>OpenTOSCA Server Details</h2>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Name:</div>
			<div class="col-md-9">${openToscaServer.name}</div>
		</div>
		<div class="row">
			<div class="col-md-2 col-md-offset-1" style="font-weight: bold;">Address:</div>
			<div class="col-md-9">
				${openToscaServer.address}
				(<a href="${openToscaServer.address}" target="_blank">Visit <span class="glyphicon glyphicon-share"></span></a>) 
			</div>
		</div>
	</div>
</div>

<div class="row" style="margin-bottom: 20px;">
<div class="col-lg-12">
    <h2>CSARs</h2>
    <div id="livedataCsars">
	    <div class="alert text-center" style="background-color: #eee;">
	    	<div id="floatingCirclesG" style="margin: auto;">
				<div class="f_circleG" id="frotateG_01">
				</div>
				<div class="f_circleG" id="frotateG_02">
				</div>
				<div class="f_circleG" id="frotateG_03">
				</div>
				<div class="f_circleG" id="frotateG_04">
				</div>
				<div class="f_circleG" id="frotateG_05">
				</div>
				<div class="f_circleG" id="frotateG_06">
				</div>
				<div class="f_circleG" id="frotateG_07">
				</div>
				<div class="f_circleG" id="frotateG_08">
				</div>
			</div>
			Loading CSARs
	  	</div>
	</div>
</div>
</div>

<div class="row" style="margin-bottom: 20px;">
<div class="col-lg-12">
	<h2>ServiceInstances</h2>
	<div id="livedataInstances">
	    <div class="alert text-center" style="background-color: #eee;">
	    	<div id="floatingCirclesG" style="margin: auto;">
				<div class="f_circleG" id="frotateG_01">
				</div>
				<div class="f_circleG" id="frotateG_02">
				</div>
				<div class="f_circleG" id="frotateG_03">
				</div>
				<div class="f_circleG" id="frotateG_04">
				</div>
				<div class="f_circleG" id="frotateG_05">
				</div>
				<div class="f_circleG" id="frotateG_06">
				</div>
				<div class="f_circleG" id="frotateG_07">
				</div>
				<div class="f_circleG" id="frotateG_08">
				</div>
			</div>
			Loading Service Instances
	  	</div>
	</div>
</div>
</div>

<script>
	window.onload = function() {
		$(document).ready(function() {
	    	$('#csarFileList').dataTable();

	    	repoLoadAsync('${basePath}/livedata/opentoscaserver/csars/${openToscaServer.id}', '#livedataCsars');
	    	repoLoadAsync('${basePath}/livedata/opentoscaserver/instances/${openToscaServer.id}', '#livedataInstances');
		});
	}
</script>

<!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deleteModalLabel">Delete OpenTosca server</h4>
            </div>
            <div class="modal-body">
                Do you really want to delete OpenTosca server <strong>${openToscaServer.name}</strong>?
            </div>
            <div class="modal-footer">
                <a href="${basePath}/deleteopentoscaserver/${openToscaServer.id}">
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
</@layout.sb_admin>