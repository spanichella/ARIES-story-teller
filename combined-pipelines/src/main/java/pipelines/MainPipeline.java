package pipelines;

import filegeneration.FileGeneration;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import types.DataType;
import types.MlModelType;
import types.PipelineType;
import types.StrategyType;

/**
 * This class extends the Main Program class by supporting the execution of
 * the main steps of the ML- and DL-based classification pipelines
 * concerning User reviews (extracted from App stores).
 *
 * @author panc
 */
public final class MainPipeline {
    private static final Logger LOGGER = Logger.getLogger(MainPipeline.class.getName());

    public static void runPipeline(
        @Nonnull Path truthFilePath, @Nonnull PipelineType pipelineType, @Nonnull DataType dataType, @Nullable MlModelType model,
        @Nonnull BigDecimal percentage, @Nullable StrategyType strategy
    ) throws Exception {
        Configuration configuration = new Configuration(truthFilePath, dataType, model, percentage, strategy);

        //Generate files for ML/DL
        LOGGER.info("Starting file generation ");
        FileGeneration.oracleAnalysis(configuration);

        //run selected pipeline
        switch (pipelineType) {
            case ML -> MLPipeline.performMlAnalysis(configuration);
            case DL -> DLPipeline.runDLPipeline(configuration);
            default -> throw new IllegalArgumentException("Unknown pipeline type: %s".formatted(pipelineType));
        }
        LOGGER.info("Program execution completed");
    }
}
