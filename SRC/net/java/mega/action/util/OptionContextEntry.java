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
package net.java.mega.action.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.java.mega.action.model.Action;

public class OptionContextEntry implements Serializable {
	private static final long serialVersionUID = -4557206511163518344L;

	private String key = null;
	private OptionContextEntry next = null;
	private Map objectMap = new HashMap();

	public OptionContextEntry(String key) {
		this.key = key;
	}

	public void put(String actionName, Action action) {
		objectMap.put(actionName, action);
	}

	public Action get(String actionName) {
		return (Action) objectMap.get(actionName);
	}

	public OptionContextEntry getNext() {
		return next;
	}

	public void setNext(OptionContextEntry next) {
		if (next == null && this.next != null) {
			this.next.clear();
		}
		
		this.next = next;
	}

	public String getKey() {
		return key;
	}

	public void clear() {
		objectMap.clear();

		if (next != null) {
			next.clear();
			next = null;
		}
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("ENTRY(");
		buffer.append("key=");
		buffer.append(key);
		buffer.append(", objects={");
		
		String objKey = null;
		boolean first = true;
		
		for (Iterator i = objectMap.keySet().iterator(); i.hasNext();) {
			objKey = (String) i.next();
			
			if (first) {
				first = false;
			} else {
				buffer.append(", ");
			}
			
			buffer.append(objKey);
			buffer.append("=");
			buffer.append(objectMap.get(objKey).getClass().getName());
		}
		
		buffer.append("}");
		
		if (next != null) {
			buffer.append(", nextEntry=");
			buffer.append(next.toString());
		}
		
		buffer.append(")");
		
		return buffer.toString();
	}
}
