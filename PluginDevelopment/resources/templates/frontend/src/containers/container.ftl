import React, { useState, useEffect } from 'react';
import { Row, Col, Typography<#if formImport??>, Button, Space, Modal</#if> } from 'antd';
import axios from 'axios';
<#if formImport??>
import { PlusOutlined, EditOutlined, DeleteOutlined} from '@ant-design/icons';
import { ${formImport}Form } from '../components/forms/${formImport}Form';
</#if>
<#if tableImport??>
import { ${tableImport} } from '../components/views/${tableImport}';
</#if>

const { Title } = Typography;

export const ${name}Container = () => {
	<#if isCreate || isEdit>
  	const [isModalVisible, setIsModalVisible] = useState(false);
  	const [isCreate, setIsCreate] = useState(true);
	</#if>
	<#if formImport??>
	const [initialValues, setInitialValues] = useState({<#list elements as el>${el} : null,</#list>});
	<#list referencedTypes?keys as idName>
	const [${idName?lower_case}Data, set${idName?cap_first}Data] = useState([]);
	const [${idName?lower_case}, set${idName?cap_first}] = useState(null);
	</#list>
	</#if>
	<#if tableImport??>
	const [data, setData] = useState([]);
	const [id, setId] = useState(null);
	const [pagins, setPagins] = useState({currentPage:1, totalPages:0, totalItems:0, pageSize:0});

	const handleGetData = () => {
		axios.get('http://${appHost}:${appPort}/${appContextPath}/${name?lower_case}', {params: {page:pagins.currentPage-1, size:pagins.pageSize}})
		  .then(function (response) {
		  	let temp = response.data;
		  	temp.map((t,indx) => t["key"] = indx);
			setData(temp);
		  })
		  .catch(function (error) {
			console.log(error);
		  });
	};
	useEffect(() => {
		axios.get('http://${appHost}:${appPort}/${appContextPath}/${name?lower_case}', {params: {page:pagins.currentPage-1, size:pagins.pageSize}})
		  .then(function (response) {
		  	let temp = response.data;
		  	temp.map((t,indx) => t["key"] = indx);
			setData(temp);
		  })
		  .catch(function (error) {
			console.log(error);
		  });
		  <#if formImport??>
		  <#list referencedTypes?keys as idName>
		  axios.get('http://${appHost}:${appPort}/${appContextPath}/${referencedTypes[idName]?lower_case}', {params: {page:pagins.currentPage-1, size:pagins.pageSize}})
		    .then(function (response) {
			  set${idName?cap_first}Data(response.data);
		    })
			.catch(function (error) {
			  console.log(error);
			});
		  </#list>
		  </#if>
	  }, []);
	  
	const rowSelection = {
		onChange: (selectedRowKeys, selectedRows) => {
			setId(selectedRows[0].${keyName});
			setInitialValues(selectedRows[0]);
		}
	};
	</#if>
	
	<#if isDelete>
	const handleDelete = () => {
		axios.delete('http://${appHost}:${appPort}/${appContextPath}/${name?lower_case}/'+id)
		  .then(function (response) {
			handleGetData();
		  })
		  .catch(function (error) {
			console.log(error);
		  });
	};
	</#if>
	
	<#if isCreate || isEdit>
	const showModal = (type) => {
		if(type==='new'){
			setInitialValues({<#list elements as el>${el} : null,</#list>});
			setIsCreate(true);
		}else{
		<#list referencedTypes?keys as idName>
			set${idName?cap_first}(initialValues['${idName}']);
		</#list>
			setIsCreate(false);
		}
		setIsModalVisible(true);
	};
	
	const handleCancel = () => {
		setIsModalVisible(false);
	};
	</#if>
	
	<#if isCreate>
	const handleOk = (values, { setSubmitting }) => {
		axios.post('http://${appHost}:${appPort}/${appContextPath}/${name?lower_case}', 
			JSON.stringify(values, null, 2), 
			{ headers:{ 'Content-Type': 'application/json'}})
		  .then(function (response) {
			setIsModalVisible(false);
			handleGetData();
		  })
		  .catch(function (error) {
			console.log(JSON.stringify(values, null, 2));
			setSubmitting(false);
			setIsModalVisible(false);
		  });
			
	};
	</#if>

	<#if isCreate>
	const handleUpdate = (values, { setSubmitting }) => {
		axios.put('http://${appHost}:${appPort}/${appContextPath}/${name?lower_case}/'+id, 
			JSON.stringify(values, null, 2),
			{ headers:{ 'Content-Type': 'application/json'}})
		  .then(function (response) {
			setIsModalVisible(false);
			handleGetData();
		  })
		  .catch(function (error) {
			console.log(JSON.stringify(values, null, 2));
			setSubmitting(false);
			setIsModalVisible(false);
		  });
			
	};
	</#if>
	
	return (
		<>
			<Row gutter={[16, 16]}>
				<Col xs={{ span: 22, offset: 1 }} sm={{ span: 22, offset: 1 }} md={{ span: 22, offset: 1 }} >
					<Title>${name}</Title>
				</Col>
			</Row>
			<#if formImport??>
			<Row gutter={[16, 16]}>
				<Col xs={{ span: 1, offset: 1 }} sm={{ span: 1, offset: 1 }} md={{ span: 1, offset: 1 }} >
					<Space direction="horizontal">
						<#if isCreate><Button type="primary" icon={<PlusOutlined />} onClick={()=>{showModal('new');}}>New</Button></#if>
						<#if isEdit><Button type="default" icon={<EditOutlined />} onClick={()=>{showModal('edit');}} disabled={id==null}>Edit</Button></#if>
						<#if isDelete><Button type="primary" icon={<DeleteOutlined />} onClick={handleDelete} disabled={id==null} danger>Delete</Button></#if>
					</Space>
				</Col>
			</Row>
			<#if isCreate || isEdit>
			<Modal
	          visible={isModalVisible}
	          destroyOnClose={true}
	          title="${formImport}"
	          footer={null}
	          onCancel={handleCancel}
	         >
	          <${formImport}Form <#if formImport??>initialValues={initialValues} <#list referencedTypes?keys as idName>${idName?lower_case}Data={${idName?lower_case}Data} ${idName?lower_case}={${idName?lower_case}}  set${idName?cap_first}={set${idName?cap_first}}</#list></#if> isCreate={isCreate} <#if isCreate>handleOk={handleOk}</#if> <#if isEdit>handleUpdate={handleUpdate}</#if>handleCancel={handleCancel}/>
	        </Modal>
			</#if>
			</#if>
			<#if tableImport??>
			<Row gutter={[16, 16]}>
				<Col xs={{ span: 22, offset: 1 }} sm={{ span: 22, offset: 1 }} md={{ span: 22, offset: 1 }} >
					<${tableImport} data={data} rowSelection={rowSelection}/>
				</Col>
			</Row>
			</#if>
		</>
	)
};
