/*  ------------------
 *  MEGA Web Framework
 *  ------------------
 *  
 *  Copyright 2006 SysVision - Consultadoria e Desenvolvimento em Sistemas de Informática, Lda.
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *  	http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.java.mega.action.output;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.java.mega.action.RequestMetaData;
import net.java.mega.action.ResponseMetaData;
import net.java.mega.action.api.ResponseProvider;
import net.java.mega.action.error.ActionException;
import net.java.mega.action.util.WorkflowControlUtil;
import net.java.mega.common.http.NavigationUtil;
import net.java.sjtools.logging.Log;
import net.java.sjtools.logging.LogFactory;

public class Redirector implements ResponseProvider {
	private static Log log = LogFactory.getLog(Redirector.class);

	private String location = null;

	public Redirector(String location) {
		this.location = location;
	}

	public void process(HttpServletRequest request, HttpServletResponse response, RequestMetaData requestMetaData,
			ResponseMetaData responseMetaData) throws ActionException {
		
		WorkflowControlUtil.markAsSameRequest(request);
		
		try {
			NavigationUtil.redirect(request, response, location);
		} catch (Exception e) {
			log.error("Error while redirecting to " + location, e);
			throw new ActionException("Error while redirecting to " + location, e);
		}
	}
}
