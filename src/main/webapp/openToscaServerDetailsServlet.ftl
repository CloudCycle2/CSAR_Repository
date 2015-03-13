<#import "layout.ftl" as layout>
<@layout.sb_admin>
<div class="row">
    <div class="col-lg-12">
    	<h2>OpenTOSCA Server-Details</h2>

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

		<form action="${basePath}/updateopentoscaserver" method="POST" class="form-horizontal">
		  <input type="hidden" name="openToscaServerId" value="${openToscaServer.id}"/>
		  <div class="form-group">
            <label class="col-sm-2 control-label" for="nameField">Name:</label>
            <div class="col-sm-10">
                  <input type="text" class="form-control" id="nameField" name="openToscaServerName" value="${openToscaServer.name}" disabled />
            </div>
          </div>
    
        <div class="form-group">
        <label class="col-sm-2 control-label" for="urlField">ContainerAPI URL:</label>
        <div class="col-sm-10">
                  <input type="url" class="form-control" id="urlField" name="address" value="${openToscaServer.address}" disabled />
        </div>
        </div>
			<div class="form-group">
		    	<div class="text-right col-sm-12">
		    	    <button tabindex="-1" class="btn btn-default" id="unlockButton">
                        <span class="glyphicon glyphicon-plus"></span>
                        Unlock</button>
		    		<button tabindex="-1" id="deleteBtn" type="button" class="btn btn-danger disabled" data-toggle="modal" data-target="#deleteModal">
    					<span class="glyphicon glyphicon-remove"></span>
    					Delete
					</button>
		      		<button id="submitBtn" type="submit" class="btn btn-success disabled" />
		      		<span class="glyphicon glyphicon-plus"></span> 
		      		  &nbsp;Save OpenTOSCA-Server MetaData
		      		</button>
				</div>
			</div>
	</form>
	</div>
</div>

<div class="row">
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

<div class="row">
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
			$('#unlockButton').click(function(e) {
					e.preventDefault();
					$('#nameField').removeAttr('disabled','disabled');
					$('#urlField').removeAttr('disabled','disabled');
					$('#deleteBtn').removeClass('disabled');
					$('#submitBtn').removeClass('disabled');
					$(this).hide();
				}
			);
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