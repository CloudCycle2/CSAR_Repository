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
		    <#assign managementPlans = PlanInvocationHelper.generateLinkToMngmtPlan(openToscaServer, live.csarID)>
			<tr>
    			<td><a href="${live.getSelfLink()}">${live.serviceInstanceID}</a></td>
				<td>${live.csarID}</td>
				<td>${live.serviceTemplateID}</td>
				<td style="text-align: center;">
				    <#if managementPlans?has_content>
				    <div class="btn-group">
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            Plans <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-right" role="menu">
                            <#list managementPlans as link>
                                <li>
                                    <a href="${link.href}" target="_blank">${link.text}</a>
                                </li>
                            </#list>
                        </ul>
                    </div>
                    <#else>
                        Nothing found
                    </#if>
				</td>
			</tr>
		</#list>
	 </tbody>
</table>
<#else>
   <div class="alert alert-warning" role="alert">No ServiceInstances found</div>
</#if>