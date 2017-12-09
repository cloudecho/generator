<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<div id="updateDialog" class="crudDialog">
	<input id="entityId" type="hidden" th:value="${r"${organization.organizationId}"}"/>
	<form id="updateForm" method="post">
	<#list columns as col><#if !col.pk>
		<div class="form-group">
			<label for="${col.name}">${col.remark}</label>
			<input id="${col.name}" type="text" class="form-control" name="${col.name}" maxlength="300" th:value="${r"${"}${simpleEntityName}.${col.name}${r"}"}"/>
		</div>
	</#if></#list>
        <div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="crud.updateSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="crud.updateDialog.close();">取消</a>
		</div>
	</form>
</div>
</html>