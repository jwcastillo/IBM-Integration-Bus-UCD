/*
* Licensed Materials - Property of IBM Corp.
* IBM UrbanCode Build
* IBM UrbanCode Deploy
* IBM UrbanCode Release
* IBM AnthillPro
* (c) Copyright IBM Corporation 2002, 2017. All Rights Reserved.
*
* U.S. Government Users Restricted Rights - Use, duplication or disclosure restricted by
* GSA ADP Schedule Contract with IBM Corp.
*/
import com.urbancode.air.plugin.AirPluginTool;
import com.urbancode.air.plugin.wmbcmp.IIBHelper;

File workDir = new File('.');

AirPluginTool apTool = new AirPluginTool(this.args[0], this.args[1])
def props = apTool.getStepProperties(System.getenv("UCD_SECRET_VAR"))
def helper = new IIBHelper(props)

def executionGroup = props['executionGroup']
def messageFlows = props['messageFlows'];

try {
    helper.setExecutionGroup(executionGroup)

    if (messageFlows) {
        for (def messageFlow in messageFlows.split("\n|,")*.trim()) {
            println "Stopping message flow ${messageFlow}";
            helper.stopMsgFlow(messageFlow);
        }
    }
    else {
        println "Stopping all message flows!"
        helper.stopAllMsgFlows();
    }
}
catch (Exception e) {
    e.printStackTrace();
    throw e;
}
finally {
    helper.cleanUp();
}
