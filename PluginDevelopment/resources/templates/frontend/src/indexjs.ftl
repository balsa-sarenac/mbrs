import React from 'react';
import ReactDOM from 'react-dom';
import "antd/dist/antd.css";
import { Tabs } from "antd"
import {
  useHistory,
  useParams,
  BrowserRouter,
  Route,
  Switch as BrowserSwitch,
} from "react-router-dom"

<#list imports as import>
import { ${import} } from './containers/${import}';
</#list>

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
        <center><h1>${appDescription}</h1></center>
      </Tabs.TabPane>
      <#list imports as import>
	      <Tabs.TabPane tab="${import}" key="${import?lower_case}">
	        <${import} />
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
