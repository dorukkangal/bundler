package in.workarounds.freighter.compiler.generator;

import com.squareup.javapoet.MethodSpec;

import java.util.List;

import javax.lang.model.element.Modifier;

import in.workarounds.freighter.compiler.Provider;
import in.workarounds.freighter.compiler.model.CargoModel;
import in.workarounds.freighter.compiler.model.FreighterModel;
import in.workarounds.freighter.compiler.util.CommonClasses;

/**
 * Created by madki on 24/10/15.
 */
public class FragmentWriter extends Writer {
    protected static final String FRAGMENT_VAR = "fragment";

    protected FragmentWriter(Provider provider, FreighterModel freighterModel, List<CargoModel> cargoList) {
        super(provider, freighterModel, cargoList);
    }

        @Override
    protected List<MethodSpec> getAdditionalHelperMethods() {
        List<MethodSpec> methods = super.getAdditionalHelperMethods();
        methods.add(
                MethodSpec.methodBuilder(UNLOAD_METHOD)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(freighterModel.getClassName(), FRAGMENT_VAR)
                        .addStatement("$L($L.getArguments()).$L($L)", UNLOAD_METHOD, FRAGMENT_VAR, INTO_METHOD, FRAGMENT_VAR)
                        .build()
        );
        return methods;
    }

    @Override
    protected List<MethodSpec> getAdditionalLoaderMethods() {
        List<MethodSpec> methods = super.getAdditionalLoaderMethods();
        methods.add(
                MethodSpec.methodBuilder(CREATE_METHOD)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(freighterModel.getClassName())
                        .addStatement("$T $L = new $T()", freighterModel.getClassName(), FRAGMENT_VAR, freighterModel.getClassName())
                        .addStatement("$L.setArguments($L())", FRAGMENT_VAR, BUNDLE_VAR)
                        .addStatement("return $L", FRAGMENT_VAR)
                        .build()
        );
        return methods;
    }
}
