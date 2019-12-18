package com.epam.wilma.router.evaluation;
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

import com.epam.wilma.domain.http.WilmaHttpRequest;
import com.epam.wilma.domain.stubconfig.StubDescriptor;
import com.epam.wilma.router.domain.ResponseDescriptorDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Evaluates the stub descriptors and returns the object necessary for the response.
 * generation.
 * @author Tunde_Kovacs
 *
 */
@Component
public class StubDescriptorEvaluator {

    /**
     * Evaluates the stub descriptors over a request and returns the object necessary for the response
     * generation.
     * @param stubDescriptors that will be evaluated
     * @param request the {@link WilmaHttpRequest} that is matched over the conditions
     * @return the data transfer object containing the response
     */
    public ResponseDescriptorDTO findResponseDescriptor(final Map<String, StubDescriptor> stubDescriptors, final WilmaHttpRequest request) {
        ResponseDescriptorDTO responseDescriptorDTO = null;
        return responseDescriptorDTO;
    }

}
