package substitutionmodelchoice;

import static org.junit.Assert.*;

import org.junit.Test;

import beast.core.parameter.IntegerParameter;
import beast.core.parameter.RealParameter;
import beast.evolution.datatype.DataType;
import beast.evolution.substitutionmodel.BinaryCovarion;
import beast.evolution.substitutionmodel.EigenDecomposition;
import beast.evolution.substitutionmodel.Frequencies;
import beast.evolution.substitutionmodel.GeneralSubstitutionModel;
import beast.evolution.substitutionmodel.HKY;
import beast.evolution.substitutionmodel.SubstitutionModel;
import beast.evolution.tree.Node;

public class SubstitutionmodelSelectorTest {
	static SubstitutionModel cov() {
		Frequencies freq = new Frequencies();
		freq.initByName("frequencies", new RealParameter(new Double[] { 0.5, 0.5 }));
		GeneralSubstitutionModel t = new GeneralSubstitutionModel();
		t.initByName("rates", new RealParameter(new Double[] { 1.0, 1.0 }),
				"frequencies", freq);
		return t;
	}

	static SubstitutionModel hkytwo() {
		HKY t = new HKY();
		t.initByName("kappa", new RealParameter(new Double[] { 2.0 }));
		return t;
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testInitAndValidate_OutOfBounds() {
		SubstitutionmodelSelector s = new SubstitutionmodelSelector();
		s.initByName("model", hkytwo(), "index", new IntegerParameter(new Integer[] { 2 }));
	}

	@Test
	public void testInitAndValidate_WithinBounds1() {
		SubstitutionmodelSelector s = new SubstitutionmodelSelector();
		s.initByName("model", hkytwo(), "index", new IntegerParameter(new Integer[] { 0 }));
	}

	@Test
	public void testInitAndValidate_WithinBounds2() {
		SubstitutionmodelSelector s = new SubstitutionmodelSelector();
		s.initByName("model", cov(), "model", hkytwo(), "index", new IntegerParameter(new Integer[] { 1 }));
	}

	@Test
	public void testPassingOnFunctions() {
		SubstitutionmodelSelector s = new SubstitutionmodelSelector();
		SubstitutionModel two = hkytwo();
		s.initByName("model", cov(), "model", two, "index", new IntegerParameter(new Integer[] { 1 }));
		assertEquals(two.canReturnComplexDiagonalization(), s.canReturnComplexDiagonalization());
		assertEquals(two.getStateCount(), s.getStateCount());
	}
}
