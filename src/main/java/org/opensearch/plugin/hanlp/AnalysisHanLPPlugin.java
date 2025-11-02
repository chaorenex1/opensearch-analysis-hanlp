package org.opensearch.plugin.hanlp;

import com.hankcs.hanlp.utility.Predefine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.opensearch.common.io.PathUtils;
import org.opensearch.common.settings.Settings;
import org.opensearch.env.Environment;
import org.opensearch.index.analysis.AnalyzerProvider;
import org.opensearch.index.analysis.TokenizerFactory;
import org.opensearch.indices.analysis.AnalysisModule;
import org.opensearch.plugin.analysis.HanLPAnalyzerProvider;
import org.opensearch.plugin.analysis.HanLPTokenizerFactory;
import org.opensearch.plugins.AnalysisPlugin;
import org.opensearch.plugins.Plugin;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Project: elasticsearch-analysis-hanlp
 * Description: Hanlp分词插件
 * Author: Kenn
 * Create: 2018-12-14 15:10
 */
public class AnalysisHanLPPlugin extends Plugin implements AnalysisPlugin {

    private static final Logger logger = LogManager.getLogger(AnalysisHanLPPlugin.class);
    /**
     * Hanlp配置文件名
     */
    public static final String CONFIG_FILE_NAME = "hanlp.properties";
    public static String PLUGIN_NAME = "analysis-hanlp";

    public AnalysisHanLPPlugin(Settings settings) {
        String home = null;
        if (Environment.PATH_HOME_SETTING.exists(settings)) {
            home = Environment.PATH_HOME_SETTING.get(settings);
        }
        if (home == null) {
            throw new IllegalStateException(Environment.PATH_HOME_SETTING.getKey() + " is not configured");
        } else {
            Path configDir = PathUtils.get(home, "config", AnalysisHanLPPlugin.PLUGIN_NAME);
            Predefine.HANLP_PROPERTIES_PATH = configDir.resolve(CONFIG_FILE_NAME).toString();

            logger.info("hanlp properties path: {}", Predefine.HANLP_PROPERTIES_PATH);
        }
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> getTokenizers() {

        Map<String, AnalysisModule.AnalysisProvider<TokenizerFactory>> extra = new HashMap<>();

        extra.put("hanlp", HanLPTokenizerFactory::getHanLPTokenizerFactory);
        extra.put("hanlp_standard", HanLPTokenizerFactory::getHanLPStandardTokenizerFactory);
        extra.put("hanlp_index", HanLPTokenizerFactory::getHanLPIndexTokenizerFactory);

        extra.put("hanlp_nlp", HanLPTokenizerFactory::getHanLPNLPTokenizerFactory);
        extra.put("hanlp_crf", HanLPTokenizerFactory::getHanLPCRFTokenizerFactory);

        extra.put("hanlp_n_short", HanLPTokenizerFactory::getHanLPNShortTokenizerFactory);
        extra.put("hanlp_dijkstra", HanLPTokenizerFactory::getHanLPDijkstraTokenizerFactory);
        extra.put("hanlp_speed", HanLPTokenizerFactory::getHanLPSpeedTokenizerFactory);

        return extra;
    }

    @Override
    public Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> getAnalyzers() {
        Map<String, AnalysisModule.AnalysisProvider<AnalyzerProvider<? extends Analyzer>>> extra = new HashMap<>();

        extra.put("hanlp", HanLPAnalyzerProvider::getHanLPAnalyzerProvider);
        extra.put("hanlp_standard", HanLPAnalyzerProvider::getHanLPStandardAnalyzerProvider);
        extra.put("hanlp_index", HanLPAnalyzerProvider::getHanLPIndexAnalyzerProvider);

        extra.put("hanlp_nlp", HanLPAnalyzerProvider::getHanLPNLPAnalyzerProvider);
        extra.put("hanlp_crf", HanLPAnalyzerProvider::getHanLPCRFAnalyzerProvider);

        extra.put("hanlp_n_short", HanLPAnalyzerProvider::getHanLPNShortAnalyzerProvider);
        extra.put("hanlp_dijkstra", HanLPAnalyzerProvider::getHanLPDijkstraAnalyzerProvider);
        extra.put("hanlp_speed", HanLPAnalyzerProvider::getHanLPSpeedAnalyzerProvider);

        return extra;
    }
}
