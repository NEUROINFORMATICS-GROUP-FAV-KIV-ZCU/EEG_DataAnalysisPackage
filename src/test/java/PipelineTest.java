import cz.zcu.kiv.Pipeline.PipelineBuilder;
import cz.zcu.kiv.Utils.Const;
import net.jcip.annotations.NotThreadSafe;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import static cz.zcu.kiv.Utils.Const.*;

/***********************************************************************************************************************
 *
 * This file is part of the Spark_EEG_Analysis project

 * ==========================================
 *
 * Copyright (C) 2017 by University of West Bohemia (http://www.zcu.cz/en/)
 *
 ***********************************************************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************
 *
 * PipelineTest, 2017/07/11 23:11 Dorian Beganovic
 *
 **********************************************************************************************************************/
public class PipelineTest{

    @Before
    public void initalizeHDFSTest() throws IOException {
        EEGTest.initalizeHDFSTest();
    }

    @After
    public void unintializeHDFSTest() throws IOException {
        EEGTest.unintializeHDFSTest();
    }


    @Test
    public void logreg_test()  throws Exception  {

        PipelineBuilder trainingPipelineBuilder = new PipelineBuilder("" +
                "info_file="+REMOTE_TEST_DATA_DIRECTORY+TRAINING_FILE +
                "&fe=dwt-8" +
                "&train_clf=logreg" +
                "&save_clf=true" +
                "&save_name="+TEST_OUTPUT_DIRECTORY+"/log_reg_demo" +
                "&result_path="+TEST_RESULTS_DIRECTORY+"/pipeline_logreg_test_result.txt"
        );
        trainingPipelineBuilder.execute();
        PipelineBuilder testingPipelineBuilder = new PipelineBuilder("" +
                "info_file="+REMOTE_TEST_DATA_DIRECTORY+TESTING_FILE+
                "&fe=dwt-8" +
                "&load_clf=logreg" +
                "&load_name="+TEST_OUTPUT_DIRECTORY+"/log_reg_demo"
        );
        testingPipelineBuilder.execute();

    }

    @Test
    public void svm_test() throws Exception {
        PipelineBuilder trainingPipelineBuilder = new PipelineBuilder("" +
                "info_file="+REMOTE_TEST_DATA_DIRECTORY+TRAINING_FILE +
                "&fe=dwt-8" +
                "&train_clf=svm" +
                "&config_step_size=1.0"+
                "&config_num_iterations=10"+
                "&config_reg_param=0.01"+
                "&config_mini_batch_fraction=1.0"+
                "&save_clf=true" +
                "&save_name="+TEST_OUTPUT_DIRECTORY+"/svm_demo" +
                "&result_path="+TEST_RESULTS_DIRECTORY+"/pipeline_svm_test_result.txt"
        );
        trainingPipelineBuilder.execute();
        PipelineBuilder testingPipelineBuilder = new PipelineBuilder("" +
                "info_file="+REMOTE_TEST_DATA_DIRECTORY+TESTING_FILE +
                "&fe=dwt-8" +
                "&load_clf=svm" +
                "&load_name="+TEST_OUTPUT_DIRECTORY+"/svm_demo"
        );
        testingPipelineBuilder.execute();

    }

}