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
							<#list standardForm.components as component>
							<#if component.visible == true>
							<FormItem name="${component.idName}" label="${component.label}" >
								<#if component.componentTypeEnum == "textBox">
									<Input name="${component.idName}" placeholder="${component.label}" />
								<#elseif component.componentTypeEnum == "comboBox">
									<Select name="${component.idName}">
										<Select.Option value={"1"}>"1"</Select.Option>
									</Select>
								<#elseif component.componentTypeEnum == "number">
									<InputNumber name="${component.idName}" placeholder="${component.label}"/>
								<#elseif component.componentTypeEnum == "checkBox">
									<Checkbox.Group name="${component.idName}"
										options={[
											{ label: "1", value: "1" },
											{ label: "2", value: "2" },
										]}
									/>
								<#elseif component.componentTypeEnum == "dateTime">
									<DatePicker showTime name="${component.idName}"/>
								<#elseif component.componentTypeEnum == "date">
									<DatePicker name="${component.idName}"/>
								<#elseif component.componentTypeEnum == "time">
									<TimePicker name="${component.idName}"/>
								<#elseif component.componentTypeEnum == "radioButton">
									<Radio.Group name="${component.idName}"
										options={[
											{ label: "1", value: "1" },
											{ label: "2", value: "2" },
										]}
									/>
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