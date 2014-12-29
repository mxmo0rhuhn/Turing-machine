/*
 * Copyright (c) 2014 - Max Schrimpf
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://joinup.ec.europa.eu/software/page/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and limitations under the Licence.
 */

package ch.zhaw.turing.Performance;

import java.util.LinkedList;

/**
 * Auf eine Turing Maschine optimierte Linked List
 *
 * @author rethab
 */
public class P_NeverNeverEnd extends LinkedList<Character> {

    private static final long serialVersionUID = 1819116069884717047L;

    @Override
    public Character pop() {
        if (isEmpty()) {
            return P_ReadWriteHead.EMPTY_VALUE;
        } else {
            return super.pop();
        }
    }

    @Override
    public Character peek() {
        throw new UnsupportedOperationException("implement me");
    }

}
