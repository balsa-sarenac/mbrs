import React, { useState } from 'react';
import { Row, Col } from 'antd';
import { Formik } from 'formik';
import { Form, FormItem, SubmitButton, ResetButton<#list imports as import><#if import??>, ${import}</#if></#list> } from 'formik-antd';

export const ${name}Form = (props) => {
	const [formLayout, setFormLayout] = useState("vertical");

	return (
		<>
			<Row>
				<Col xs={{ span: 22, offset: 1 }} sm={{span: 22, offset: 1 }} md={{ span: 22, offset: 1 }}>
					<Formik
						initialValues={props.initialValues} 
						onSubmit={(values, { setSubmitting })=>{if(props.isCreate){props.handleOk(values, { setSubmitting });}else{props.handleUpdate(values, { setSubmitting });}}}
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
