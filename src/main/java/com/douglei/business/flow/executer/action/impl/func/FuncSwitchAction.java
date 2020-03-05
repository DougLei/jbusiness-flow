package com.douglei.business.flow.executer.action.impl.func;

import com.douglei.business.flow.executer.action.Action;

/**
 * 
 * @author DougLei
 */
public class FuncSwitchAction extends Action {
	private SwitchGroup[] groups;
	
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
	
	
	
	private class SwitchGroup{
		
		private Action[] actions;
		
		void execute() {
			
		}
	}
}