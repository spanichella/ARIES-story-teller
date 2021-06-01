package pipelines;

import configfile.ConfigFileReader;
import filegeneration.FileGeneration;
import filegeneration.XMLInitializer;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
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
        Path pathConfigFile;
        try {
            pathConfigFile = XMLInitializer.createXML(truthFilePath, dataType, model, percentage, strategy);
        } catch (TransformerException | ParserConfigurationException | RuntimeException exception) {
            throw new RuntimeException("Generating the XML Failed", exception);
        }

        LOGGER.log(Level.INFO, "Path of ConfigFile: %s".formatted(pathConfigFile));

        //Read Config file
        ConfigFileReader configFileReader = new ConfigFileReader(pathConfigFile);
        //Generate files for ML/DL
        LOGGER.info("Starting file generation ");
        FileGeneration.oracleAnalysis(configFileReader);

        //run selected pipeline
        switch (pipelineType) {
            case ML -> MLPipeline.performMlAnalysis(configFileReader);
            case DL -> DLPipeline.runDLPipeline(configFileReader);
            default -> throw new IllegalArgumentException("Unknown pipeline type: %s".formatted(pipelineType));
        }
        LOGGER.info("Program execution completed");
    }
}
