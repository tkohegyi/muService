package com.epam.wilma.maintainer.task.timelimit;

/*==========================================================================
Copyright since 2013, EPAM Systems

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

import com.epam.wilma.common.helper.CurrentDateProvider;
import com.epam.wilma.maintainer.configuration.MaintainerConfigurationAccess;
import com.epam.wilma.maintainer.configuration.domain.MaintainerProperties;
import com.epam.wilma.maintainer.task.MaintainerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * This task runs scheduled as defined in wilma.conf.properties.
 * Used to maintain log files by deleting old ones based on a time limit.
 * @author Marton_Sereg
 *
 */
@Component
public class TimeLimitMaintainerTask implements MaintainerTask {

    private static final int SECONDS_IN_HOUR = 3600;
    private static final int SECONDS_IN_DAY = 86400;
    private final Map<Character, Integer> multipliers;
    private String timeLimit;
    private Integer timeLimitInSeconds;

    @Autowired
    @Qualifier("simpleDateFormatterForFiles")
    private SimpleDateFormat simpleDateFormat;
    @Autowired
    private CurrentDateProvider currentDateProvider;
    @Autowired
    private MaintainerConfigurationAccess configurationAccess;

    /**
     * Constructor that initialize the possible multiplier for the time limit.
     */
    public TimeLimitMaintainerTask() {
        multipliers = new HashMap<>();
        multipliers.put('H', SECONDS_IN_HOUR);
        multipliers.put('D', SECONDS_IN_DAY);
        multipliers.put('S', 1);
    }

    @Override
    public void run() {
        // here we can do anything
    }

    @Override
    public void logParameters() {
        getTimeLimit();
    }


    private void getTimeLimit() {
        if (timeLimit == null) {
            MaintainerProperties properties = configurationAccess.getProperties();
            timeLimit = properties.getTimeLimit();
            timeLimitInSeconds = valueOfProperty() * multipliers.get(typeOfProperty());
        }
    }

    private int valueOfProperty() {
        return Integer.parseInt(timeLimit.substring(0, timeLimit.length() - 1));
    }

    private Character typeOfProperty() {
        return timeLimit.charAt(timeLimit.length() - 1);
    }

}
