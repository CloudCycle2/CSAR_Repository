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
                <a href="${wineryServer.address}">Visit Winery</a>
            </div>
        </div>
    </div>
    <!-- /.panel-body -->
</div>

<form action="${basePath}/updatewineryserver/${wineryServer.id}" method="POST" class="form-horizontal">
  <div class="form-group">
    <label for="inputWineryServerName" class="col-sm-1 control-label">Name</label>
    <div class="col-sm-11">
      <input name="wineryServerName" type="text" class="form-control" id="inputWineryServerName" value="${wineryServer.name}" disabled />
    </div>
  </div>
  <div class="form-group">
    <label for="inputWineryServerUrl" class="col-sm-1 control-label">URL</label>
    <div class="col-sm-11">
      <input name="wineryServerUrl" type="text" class="form-control" id="inputWineryServerUrl" value="${wineryServer.address}" disabled />
    </div>
  </div>
  <#if wineryServer.address?ends_with("servicetemplates") || wineryServer.address?ends_with("servicetemplates/")>
  <div class="form-group">
    <label class="col-sm-1 control-label"><span class="glyphicon glyphicon-warning-sign form-control-feedback" aria-hidden="true"></span></label>
    <div class="col-sm-11 text-warning">
      Winery automatically redirects your browser to /servicetemplates, but this is not the root URL of winery.<br />
      Are you sure your settings are correct?
    </div>
  </div>
  </#if>
  <div class="form-group">
    <div class="text-right col-sm-12">
        <button tabindex="-1" class="btn btn-default" id="unlockButton">
            <span class="glyphicon glyphicon-plus"></span> 
            Unlock</button>
    	<button tabindex="-1" id="deleteBtn" type="button" class="btn btn-danger disabled" data-toggle="modal" data-target="#deleteModal">
        	<span class="glyphicon glyphicon-remove"></span>
        	Delete
    	</button>
      	<button id="submitBtn" type="submit" class="btn btn-success disabled">
      		<span class="glyphicon glyphicon-ok"></span>
      		Update winery server
      	</button>
    </div>
  </div>
</form>
<a name="serviceTemplates"></a>
<h3>Available ServiceTemplates</h3>
<hr />
<div id="serviceTemplates">
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
			Loading Service Templates
	  	</div>
	</div>

<script>
    window.onload = function() {
        $(document).ready(function() {
            $('#unlockButton').click(function(e) {
                    e.preventDefault();
                    $('#inputWineryServerName').removeAttr('disabled','disabled');
                    $('#inputWineryServerUrl').removeAttr('disabled','disabled');
                    $('#deleteBtn').removeClass('disabled');
                    $('#submitBtn').removeClass('disabled');
                    $(this).hide();
                }
            );
            
            repoLoadAsync('${basePath}/livedata/wineryserver/templates/${wineryServer.id}', '#serviceTemplates');
        });
    }
</script>

 <!-- Delete Modal -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Delete Winery server</h4>
            </div>
            <div class="modal-body">
                Do you really want to delete <strong>${wineryServer.name}</strong>?
            </div>
            <div class="modal-footer">
                <a href="${basePath}/deletewineryserver/${wineryServer.id}">
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