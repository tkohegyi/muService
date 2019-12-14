package com.epam.wilma.maintainer.task.filelimit;
/*==========================================================================
Copyright since 2013, EPAM Systems + altered for muService, copyright Tamas Kohegyi since 2019

This file is part of Wilma.

Wilma is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Wilma is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Wilma.  If not, see <http://www.gnu.org/licenses/>.
===========================================================================*/

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.epam.wilma.common.helper.LogFilePathProvider;
import com.epam.wilma.maintainer.configuration.MaintainerConfigurationAccess;
import com.epam.wilma.maintainer.configuration.domain.MaintainerProperties;
import com.epam.wilma.maintainer.domain.DeletedFileProvider;
import com.epam.wilma.maintainer.task.MaintainerTask;

/**
 * This task runs scheduled as defined in wilma.conf.properties.
 * Used to maintain log files by deleting old ones based on a file limit.
 * @author Marton_Sereg, Tamas Kohegyi
 *
 */
@Component
public class FileLimitMaintainerTask implements MaintainerTask {

    private static final int TIMESTAMP_END_INDEX = 19;
    private final Logger logger = LoggerFactory.getLogger(FileLimitMaintainerTask.class);
    private Integer fileLimit;

    @Autowired
    @Qualifier("message")
    private FileFilter messageFileFilter;
    @Autowired
    private LogFilePathProvider logFilePath;
    @Autowired
    private MaintainerConfigurationAccess configurationAccess;
    @Autowired
    private DeletedFileProvider deletedFileProvider;

    @Override
    public void run() {
    }

    @Override
    public void logParameters() {
        getFileLimit();
        logger.info("Filelimit method is used to maintain log files with parameters: filelimit: " + fileLimit);
    }

    private void getFileLimit() {
        if (fileLimit == null) {
            MaintainerProperties properties = configurationAccess.getProperties();
            fileLimit = properties.getFileLimit();
        }
    }


}
