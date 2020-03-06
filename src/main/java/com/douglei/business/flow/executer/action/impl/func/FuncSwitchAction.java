package com.douglei.business.flow.executer.action.impl.func;

import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class FuncSwitchAction extends Action {
	private SwitchGroup[] groups;
	
	
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	private class SwitchGroup{
		
		private Action[] actions;
		
		void execute() {
			
		}
	}
}