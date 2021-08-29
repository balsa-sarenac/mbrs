import React, { useState } from 'react';
import { Row, Col<#if (formAssociationEndElements?size>0)>, Button</#if> } from 'antd';
import { Formik } from 'formik';
import { Form, FormItem, SubmitButton, ResetButton<#list imports as import><#if import??>, ${import}</#if></#list> } from 'formik-antd';
<#if (formAssociationEndElements?size>0)>
import { Chooser } from '../common/Chooser';
</#if>
export const ${name}Form = (props) => {
	const [formLayout, setFormLayout] = useState("vertical");
	const [selectionType, setSelectionType] = useState("radio");
	const [selectedRowKeys, setSelectedRowKeys] = useState([]);
	<#if (formAssociationEndElements?size>0)>
	const [chooserName, setChooserName] = useState('');
	const [selectedRow, setSelectedRow] = useState(null);
	const [isModalVisible, setIsModalVisible] = useState(false);
	const [record, setRecord] = useState(null);
	<#list formAssociationEndElements as component>
	const [is${component.idName?cap_first}ModalVisible, setIs${component.idName?cap_first}ModalVisible] = useState(false);
	</#list>
	const rowSelection = {
		selectedRowKeys,
		onChange: (selectedRowKeys, selectedRows) => {
			setSelectedRowKeys(selectedRowKeys);
			if(selectionType=='radio'){
				setSelectedRow(selectedRows[0]);
			}else{
				setSelectedRow(selectedRows);
			}
		}
	};
	const handleCancel = () => {
		setSelectedRowKeys([]);
		setIsModalVisible(false);
		<#list formAssociationEndElements as component>
		setIs${component.idName?cap_first}ModalVisible(false);		
		</#list>
	};
	const handleOk = () => {
		setSelectedRowKeys([]);
		setIsModalVisible(false);
		<#list formAssociationEndElements as component>
		setIs${component.idName?cap_first}ModalVisible(false);		
		</#list>
		<#list formAssociationEndElements as component>
		if(is${component.idName?cap_first}ModalVisible){
			props.set${component.idName?cap_first}(selectedRow);
		}
		</#list>
	};
	</#if>
	return (
		<>
			<#if (formAssociationEndElements?size>0)>
			<Chooser data={record} isModalVisible={isModalVisible} selectionType={selectionType} rowSelection={rowSelection} name={chooserName} handleCancel={handleCancel} handleOk={handleOk}/>
			</#if>
			<Row>
				<Col xs={{ span: 22, offset: 1 }} sm={{span: 22, offset: 1 }} md={{ span: 22, offset: 1 }}>
					<Formik
						initialValues={props.initialValues} 
						onSubmit={(values, { setSubmitting })=>{<#list formAssociationEndElements as component>values['${component.idName}']=props.${component.idName?lower_case};</#list> if(props.isCreate){props.handleOk(values, { setSubmitting });}else{props.handleUpdate(values, { setSubmitting });}}}
					>
						<Form
							layout={formLayout}
							scrollToFirstError>
							<#list formElements as component>
							<#if component.visible == true>
							<#if component.type.name=="Boolean">
							<#if component.componentTypeEnum=="radioButton">
							<FormItem name="${component.idName}" label="${component.label}" >
								<Radio.Group name="${component.idName}">
									<Radio key={1} name="${component.idName}" value={true}>Yes</Radio>
									<Radio key={0} name="${component.idName}" value={false}>No</Radio>
								</Radio.Group>							
							</FormItem>
							<#else>
							<FormItem name="${component.idName}" label="${component.label}" >
								<Checkbox name="${component.idName}"></Checkbox>						
							</FormItem>	
							</#if>
							<#continue>
							</#if>
							<FormItem name="${component.idName}" label="${component.label}" >
								<#if component.componentTypeEnum == "textBox">
									<Input name="${component.idName}" placeholder="${component.label}" <#if component.isKey??>disabled={true}</#if>/>
								<#elseif component.componentTypeEnum == "comboBox">
									<#if component.type??>
									<Select name="${component.idName}">
									{props.${component.type.name?lower_case}.map((value, index) => {
										return (
											<Select.Option key={value.id} value={value.id}>{value.id}</Select.Option>
										)
									})}
									</Select>
									<#else>
									<Select name="${component.idName}">
									</Select>
									</#if>
								<#elseif component.componentTypeEnum == "number">
									<InputNumber name="${component.idName}" placeholder="${component.label}" <#if component.isKey??>disabled={true}</#if>/>
								<#elseif component.componentTypeEnum == "checkBox">
									<#if component.type??>
									<Checkbox.Group name="${component.idName}">
									{props.${component.type.name?lower_case}.map((value, index) => {
										return (
											<Checkbox key={value.id} value={value.id}>{value.id}</Checkbox>
										)
									})}
									</Checkbox.Group>
									<#else>
									<Checkbox.Group name="${component.idName}"
										options={[]}
									/>
									</#if>
								<#elseif component.componentTypeEnum == "dateTime">
									<DatePicker showTime name="${component.idName}"/>
								<#elseif component.componentTypeEnum == "date">
									<DatePicker name="${component.idName}"/>
								<#elseif component.componentTypeEnum == "time">
									<TimePicker name="${component.idName}"/>
								<#elseif component.componentTypeEnum == "radioButton">
									<#if component.type??>
									<Radio.Group name="${component.idName}">
									{props.${component.type.name?lower_case}.map((value, index) => {
										return (
									    	  <Radio key={value.id} value={value.id}>{value.id}</Radio>
										)
									})}
									</Radio.Group>
									<#else>
									<Radio.Group name="${component.idName}"
										options={[]}
									/>
									</#if>
								<#elseif component.componentTypeEnum == "textArea">
									<Input.TextArea name="${component.idName}" placeholder="${component.label}" />
								<#else>
									NULL
								</#if>
							</FormItem>
							</#if>
							</#list>
							<#list formAssociationEndElements as component>
							<FormItem name="${component.idName}" label="${component.label}" >
								<Button onClick={()=>{<#if (component.upper==1)>setSelectionType('radio'); if(props.initialValues.${component.idName}!=null){setSelectedRowKeys([props.initialValues.${component.idName}.id]);}<#else>setSelectionType('check'); if(props.initialValues.${component.idName}!=null){setSelectedRowKeys(props.initialValues.${component.idName}.map(t => t.id));}</#if> setChooserName('${component.idName?cap_first}'); setRecord(props.${component.idName?lower_case}Data); setIs${component.idName?cap_first}ModalVisible(true); setIsModalVisible(true)}}>Browse...</Button>							
							</FormItem>
							</#list>
							<FormItem name="submit" >
								<SubmitButton> 
									Submit
								</SubmitButton>
							</FormItem>
							<FormItem name="reset" >
								<ResetButton>
									Reset
								</ResetButton>
							</FormItem>
						</Form>
					</Formik>
				</Col>
			</Row>
		</>
	)
};
