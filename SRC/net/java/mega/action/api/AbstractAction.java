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

package net.java.mega.action.api;

import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.java.mega.action.ActionManager;
import net.java.mega.action.RequestProcessor;
import net.java.mega.action.error.ActionException;
import net.java.mega.action.error.WorkflowError;
import net.java.mega.action.model.Action;
import net.java.mega.action.util.ActionMessageUtil;
import net.java.mega.action.util.RequestProcessorUtil;
import net.java.mega.action.util.WorkflowControlUtil;
import net.java.mega.common.resource.LocaleUtil;
import net.java.sjtools.util.TextUtil;

public abstract class AbstractAction implements Action, Serializable {
	private static final long serialVersionUID = 1502850121248385461L;

	private Properties config = null;

	public void workflowError() throws WorkflowError {
		throw new WorkflowError(this, getRequestToken());
	}

	public void setProperties(Properties config) {
		this.config = config;
	}

	public String getProperty(String name) {
		return config.getProperty(name);
	}

	public abstract void onLoad();

	public void gotoAction(Class clazz) {
		gotoAction(clazz, null);
	}

	public void gotoAction(Class clazz, Object obj) {
		getRequestProcessor().gotoAction(clazz, obj);
	}

	public void gotoAction(Action action) {
		gotoAction(action, null);
	}

	public void gotoAction(Action action, Object obj) {
		getRequestProcessor().gotoAction(action, obj);
	}

	public void gotoAction(String path) {
		gotoAction(path, null);
	}

	public void gotoAction(String path, Object obj) {
		getRequestProcessor().gotoAction(path, obj);
	}

	public Action getAction(Class clazz) {
		return getRequestProcessor().getActionInstance(clazz);
	}

	public void addMessage(Message message) {
		getRequestProcessor().addMessage(message);
	}

	public void addMessage(String key, Message message) {
		getRequestProcessor().addMessage(key, message);
	}

	public HttpServletRequest getHttpServletRequest() {
		return getRequestProcessor().getHttpServletRequest();
	}

	public HttpServletResponse getHttpServletResponse() {
		return getRequestProcessor().getHttpServletResponse();
	}

	public HttpSession getHttpSession() {
		return getRequestProcessor().getHttpSession();
	}

	public ServletContext getServletContext() {
		return getRequestProcessor().getServletContext();
	}

	public Locale getLocale() {
		return LocaleUtil.getUserLocate(getHttpServletRequest());
	}

	public void setLocale(Locale locale) {
		LocaleUtil.setUserLocate(getHttpServletRequest(), locale);
	}

	public void invalidateSession() {
		getRequestProcessor().invalidateSession();
	}

	public void removeSessionAction(Class clazz) {
		getRequestProcessor().removeSessionAction(clazz);
	}

	public void removeSessionAction(Action action) {
		getRequestProcessor().removeSessionAction(action);
	}

	public void removeSessionAction(String path) {
		getRequestProcessor().removeSessionAction(path);
	}

	public boolean containsMessage(Locale locale, String key) {
		return !TextUtil.isEmptyString(ActionMessageUtil.getMessage(key, locale));
	}

	public String getRequestToken() {
		return WorkflowControlUtil.getUserToken(getRequestProcessor().getRequestMetaData().getParameters());
	}

	public String getNextRequestToken() {
		return WorkflowControlUtil.getCurrentToken(getHttpServletRequest());
	}

	public ResponseProvider getDefaultResponseProvider() throws ActionException {
		return ActionManager.getInstance().getResponseProvider(this);
	}

	private RequestProcessor getRequestProcessor() {
		return RequestProcessorUtil.getCurrentRequestProcessor();
	}
}
