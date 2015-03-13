<#if errorMessages??>
    <div class="alert alert-warning" role="alert">${errorMessages}</div>
<#elseif liveEntries?has_content>
<table id="ciList" class="table table-striped table-bordered" border="1">
	<thead>
		<tr>
			<th>Service Instance Id</th>
			<th>CSAR Id</th>
			<th>Service Template Id</th>
			<th>Management Plans</th>
		</tr>
	</thead>
	<tbody>
		<#list liveEntries as live>
			<tr>
    			<td><a href="${live.getSelfLink()}">${live.serviceInstanceID}</a></td>
				<td>${live.csarID}</td>
				<td>${live.serviceTemplateID}</td>
				<td>
				<#list PlanInvocationHelper.generateLinkToMngmtPlan(openToscaServer, live.csarID) as link>
				  <a href="${link.href}">${link.text}</a>
				</#list> 
				</td>
			</tr>
		</#list>
	 </tbody>
</table>
<#else>
   <div class="alert alert-warning" role="alert">No ServiceInstances found</div>
</#if>