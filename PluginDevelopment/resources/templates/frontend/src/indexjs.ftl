import React from 'react';
import ReactDOM from 'react-dom';
import "antd/dist/antd.css";
import { Row, Col, Tabs, Typography } from "antd";

import {
  useHistory,
  useParams,
  BrowserRouter,
  Route,
  Switch as BrowserSwitch,
} from "react-router-dom";

<#list imports as import>
import { ${import}Container } from './containers/${import}Container';
</#list>

const { Title } = Typography;

const Root = () => {
  const { tab } = useParams();
  const history = useHistory();
  return (
    <Tabs
      tabBarStyle={{ marginLeft: 40 }}
      animated={false}
      onChange={key => {
        history.push(key)
      }}
      activeKey={tab || "/"}
    >
      <Tabs.TabPane tab="Home" key={"/"}>
       <>
			<Row>
				<Col xs={{ span: 22, offset: 1 }} sm={{ span: 22, offset: 1 }} md={{ span: 22, offset: 1 }}>
					<center><Title>${appDescription}</Title></center>
				</Col>
			</Row>
		</>
      </Tabs.TabPane>
      <#list imports as import>
	      <Tabs.TabPane tab="${import}" key="${import?lower_case}">
	        <${import}Container />
	      </Tabs.TabPane>
	  </#list>
    </Tabs>
  )
}

ReactDOM.render(
  <BrowserRouter>
    <BrowserSwitch>
      <Route path="/" exact={true} component={Root} />
      <Route path="/:tab" component={Root} />
    </BrowserSwitch>
  </BrowserRouter>,
  document.getElementById('root')
);
