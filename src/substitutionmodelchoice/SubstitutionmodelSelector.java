package substitutionmodelchoice;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import beast.core.BEASTObject;
import beast.core.CalculationNode;
import beast.core.Function;
import beast.core.Input;
import beast.core.Input.Validate;
import beast.core.parameter.IntegerParameter;
import beast.core.Loggable;
import beast.evolution.datatype.DataType;
import beast.evolution.substitutionmodel.EigenDecomposition;
import beast.evolution.substitutionmodel.SubstitutionModel;
import beast.evolution.tree.Node;

public class SubstitutionmodelSelector extends SubstitutionModel.Base implements Loggable {
	public Input<List<SubstitutionModel>> modelsInput = new Input<List<SubstitutionModel>>("model",
			"The substitution models between which the Selector selects", new ArrayList<SubstitutionModel>(),
			Validate.REQUIRED);

	public Input<IntegerParameter> indexInput = new Input<IntegerParameter>("index", "model selection indices",
			Validate.REQUIRED);

	@Override
	public void initAndValidate() {
		Integer nModels = modelsInput.get().size();
		if (nModels < indexInput.get().getValue()) {
			throw new ArrayIndexOutOfBoundsException(
					String.format("index is %d, but only %d models were given.", indexInput.get().getValue(), nModels));

		}
	}

	@Override
	public void getTransitionProbabilities(Node node, double startTime, double endTime, double rate, double[] matrix) {
		modelsInput.get().get(indexInput.get().getValue()).getTransitionProbabilities(node, startTime, endTime, rate,
				matrix);
	}

	@Override
	public double[] getRateMatrix(Node node) {
		return modelsInput.get().get(indexInput.get().getValue()).getRateMatrix(node);
	}

	@Override
	public double[] getFrequencies() {
		return modelsInput.get().get(indexInput.get().getValue()).getFrequencies();
	}

	@Override
	public int getStateCount() {
		return modelsInput.get().get(indexInput.get().getValue()).getStateCount();
	}

	@Override
	public EigenDecomposition getEigenDecomposition(Node node) {
		return modelsInput.get().get(indexInput.get().getValue()).getEigenDecomposition(node);
	}

	@Override
	public boolean canReturnComplexDiagonalization() {
		return modelsInput.get().get(indexInput.get().getValue()).canReturnComplexDiagonalization();
	}

	@Override
	public boolean canHandleDataType(DataType dataType) {
		return modelsInput.get().get(indexInput.get().getValue()).canHandleDataType(dataType);
	}

	@Override
	public void init(PrintStream out) {
		out.print(getID());
	}

	@Override
	public void log(int sample, PrintStream out) {
		out.print(indexInput.get().getValue());
	}

	@Override
	public void close(PrintStream out) {
		// nothing to do
	}
}
