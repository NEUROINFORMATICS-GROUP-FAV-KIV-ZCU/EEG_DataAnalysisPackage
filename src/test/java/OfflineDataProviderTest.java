import cz.zcu.kiv.DataTransformation.DataProviderUtils;
import cz.zcu.kiv.DataTransformation.OffLineDataProvider;

import cz.zcu.kiv.Utils.Const;
import net.jcip.annotations.NotThreadSafe;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.*;

import java.io.IOException;
import java.util.List;

import static cz.zcu.kiv.Utils.Const.REMOTE_TEST_DATA_DIRECTORY;
import static cz.zcu.kiv.Utils.Const.TRAINING_FILE;

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
 * Baseline, 2017/05/25 22:05 Dorian Beganovic
 *
 **********************************************************************************************************************/
public class OfflineDataProviderTest {
    private static Log logger = LogFactory.getLog(OffLineDataProvider.class);

    @Before
    public void initalizeHDFSTest() throws IOException {
        EEGTest.initalizeHDFSTest();
    }

    @After
    public void unintializeHDFSTest() throws IOException {
        EEGTest.unintializeHDFSTest();
    }

    @Test
    public void testLoadingInfoTxtFile(){
        try {
            String[] files = {REMOTE_TEST_DATA_DIRECTORY+TRAINING_FILE};
            OffLineDataProvider odp =
                    new OffLineDataProvider(files);
            odp.loadData();
            List<double[][]> epochs = odp.getData();
            List<Double> targets = odp.getDataLabels();
            logger.info("loaded " + epochs.size() + " epochs, each with size " + epochs.get(0).length + "x" + epochs.get(0)[0].length );
            logger.info("loaded " + targets.size() + " labels");

            assert epochs.size() == 11;
            assert epochs.get(0).length == 3;
            assert epochs.get(0)[0].length == 750;
            DataProviderUtils.writeEpochsToCSV(epochs);

            double epochsSum = 0;
            for (double[][] epoch : epochs){
                for (int i = 0; i < epoch.length; i++){
                    double rowSum = 0;
                    for (int j = 0; j < epoch[i].length; j++){
                        rowSum += epoch[i][j];
                    }
                    epochsSum += rowSum;
                }
            }
            logger.info("Sum of epochs is" + epochsSum);
            assert epochsSum == (-253772.18676757812);

            int targetsSum = 0;
            for (double target : targets){
                targetsSum += target;
            }
            logger.info("Sum of targets is" + targetsSum);
            assert targetsSum == 5;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadingFile1(){
        try {
            String[] files = {REMOTE_TEST_DATA_DIRECTORY+"/DoD/DoD_2015_02.eeg","4"};
            OffLineDataProvider odp =
                    new OffLineDataProvider(files);
            odp.loadData();
            List<double[][]> epochs = odp.getData();
            List<Double> targets = odp.getDataLabels();

            logger.info("loaded " + epochs.size() + " epochs, each with size " + epochs.get(0).length + "x" + epochs.get(0)[0].length );
            logger.info("loaded " + targets.size() + " labels");
            assert epochs.size() == 27;
            assert epochs.get(0).length == 3;
            assert epochs.get(0)[0].length == 750;


            double sum = 0;
            for (double[][] epoch : epochs){
                for (int i = 0; i < epoch.length; i++){
                    double rowSum = 0;
                    for (int j = 0; j < epoch[i].length; j++){
                        rowSum += epoch[i][j];
                    }
                    sum += rowSum;
                }
            }
            logger.info("Sum of epochs is" + sum);

            int targetsSum = 0;
            for (double target : targets){
                targetsSum += target;
            }
            logger.info("Sum of targets is" + targetsSum);
            assert targetsSum == 13;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
