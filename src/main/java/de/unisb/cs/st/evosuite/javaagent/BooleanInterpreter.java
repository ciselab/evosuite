/**
 * 
 */
package de.unisb.cs.st.evosuite.javaagent;

import java.util.List;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.analysis.AnalyzerException;
import org.objectweb.asm.tree.analysis.BasicInterpreter;
import org.objectweb.asm.tree.analysis.BasicValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gordon Fraser
 * 
 */
public class BooleanInterpreter extends BasicInterpreter {

	public final static BasicValue BOOLEAN = new BasicValue(null);

	private static Logger logger = LoggerFactory.getLogger(BooleanInterpreter.class);

	@Override
	public BasicValue newOperation(AbstractInsnNode insn) throws AnalyzerException {

		if (insn.getOpcode() == ICONST_0) {
			logger.info("Found BOOLEAN");
			return BOOLEAN;
		} else if (insn.getOpcode() == ICONST_1) {
			logger.info("Found BOOLEAN");
			return BOOLEAN;
		} else {
			return super.newOperation(insn);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public BasicValue naryOperation(AbstractInsnNode insn, List values)
	        throws AnalyzerException {
		if (insn.getOpcode() == INVOKESTATIC) {
			MethodInsnNode mn = (MethodInsnNode) insn;

			if (mn.name.equals("getDistance") && values.size() == 1) {
				if (values.get(0) == BOOLEAN)
					return BOOLEAN;
			}
		}
		return super.naryOperation(insn, values);

	}

}
