<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<div id="createDialog" class="crudDialog">
	<form id="createForm" method="post">
	<#list columns as col><#if !col.pk>
		<div class="form-group">
			<label for="${col.name}">${col.remark}</label>
			<input id="${col.name}" type="text" class="form-control" name="${col.name}" maxlength="300"/>
		</div>
    </#if></#list>
		<div class="form-group text-right dialog-buttons">
			<a class="waves-effect waves-button" href="javascript:;" onclick="crud.createSubmit();">保存</a>
			<a class="waves-effect waves-button" href="javascript:;" onclick="crud.createDialog.close();">取消</a>
		</div>
	</form>
</div>
</html>