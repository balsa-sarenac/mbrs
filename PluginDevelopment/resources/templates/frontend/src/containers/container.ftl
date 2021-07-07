import React, { useState, useEffect } from 'react';
import { Row, Col, Typography<#if formImport??>, Button, Space, Modal</#if> } from 'antd';
import axios from 'axios';
<#if formImport??>
import { PlusOutlined, EditOutlined, DeleteOutlined} from '@ant-design/icons';
import { ${formImport} } from '../components/forms/${formImport}';
</#if>
<#if tableImport??>
import { ${tableImport} } from '../components/views/${tableImport}';
</#if>

const { Title } = Typography;

export const ${name}Container = () => {
	<#if isCreate || isEdit>
  	const [isModalVisible, setIsModalVisible] = useState(false);
	</#if>
	<#if tableImport??>
	const [data, setData] = useState([]);
	const [id, setId] = useState(null);
	
	const handleGetData = () => {
		axios.get('http://${appHost}:${appPort}/${appContextPath}/${name?lower_case}')
		  .then(function (response) {
		  	let temp = response.data;
		  	temp.forEach(t => t["key"] = t.id);
			setData(temp);
		  })
		  .catch(function (error) {
			console.log(error);
		  });
	};
	useEffect(() => {
		axios.get('http://${appHost}:${appPort}/${appContextPath}/${name?lower_case}')
		  .then(function (response) {
		  	let temp = response.data;
		  	temp.forEach(t => t["key"] = t.id);
			setData(temp);
		  })
		  .catch(function (error) {
			console.log(error);
		  });
	  }, []);
	  
	const rowSelection = {
		onChange: (selectedRowKeys, selectedRows) => {
			setId(selectedRows[0].id);
		}
	};
	</#if>
	
	<#if isDelete>
	const handleDelete = () => {
		axios.delete('http://localhost:8080/api/'+id)
		  .then(function (response) {
			handleGetData();
		  })
		  .catch(function (error) {
			console.log(error);
		  });
	};
	</#if>
	
	<#if isCreate || isEdit>
	const showModal = () => {
		setIsModalVisible(true);
	};
	
	const handleOk = () => {
		setIsModalVisible(false);
	};
	
	const handleCancel = () => {
		setIsModalVisible(false);
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
						<#if isCreate><Button type="primary" icon={<PlusOutlined />} onClick={showModal}>New</Button></#if>
						<#if isEdit><Button type="default" icon={<EditOutlined />} onClick={showModal} disabled={id==null}>Edit</Button></#if>
						<#if isDelete><Button type="primary" icon={<DeleteOutlined />} onClick={handleDelete} disabled={id==null} danger>Delete</Button></#if>
					</Space>
				</Col>
			</Row>
			<#if isCreate || isEdit>
			<Modal
	          visible={isModalVisible}
	          title="${formImport}"
	          onOk={handleOk}
	          onCancel={handleCancel}
	          footer={[
	            <Button key="cancel" onClick={handleCancel}>
	              Cancel
	            </Button>,
	            <Button key="submit" type="primary" onClick={handleOk}>
	              Submit
	            </Button>
	          ]}
	         >
	          <${formImport} />
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
