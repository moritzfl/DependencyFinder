/*
 *  Copyright (c) 2001-2009, Jean Tessier
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *      * Redistributions of source code must retain the above copyright
 *        notice, this list of conditions and the following disclaimer.
 *
 *      * Redistributions in binary form must reproduce the above copyright
 *        notice, this list of conditions and the following disclaimer in the
 *        documentation and/or other materials provided with the distribution.
 *
 *      * Neither the name of Jean Tessier nor the names of his contributors
 *        may be used to endorse or promote products derived from this software
 *        without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jeantessier.dependency;

import java.io.*;
import java.util.*;

public class TextCyclePrinter implements CyclePrinter {
    protected PrintWriter out;

    private String indentText = "    ";
    private int indentLevel;

    public TextCyclePrinter(PrintWriter out) {
        this.out = out;
    }

    public void setIndentText(String indentText) {
        this.indentText = indentText;
    }

    public void visitCycles(Collection<Cycle> cycles) {
        for (Cycle cycle : cycles) {
            visitCycle(cycle);
        }
    }

    public void visitCycle(Cycle cycle) {
        Node currentNode;
        Node previousNode;

        Iterator<Node> i = cycle.getPath().iterator();
        currentNode = i.next();
        visitFirstNode(currentNode);

        while (i.hasNext()) {
            previousNode = currentNode;
            currentNode = i.next();
            visitNode(previousNode, currentNode);
        }

        previousNode = currentNode;
        currentNode = cycle.getPath().iterator().next();
        visitNode(previousNode, currentNode);
    }

    private void visitFirstNode(Node node) {
        indentLevel = 0;
        printFirstNode(node);
    }

    private void visitNode(Node previousNode, Node currentNode) {
        indentLevel++;
        indent();
        printNode(previousNode, currentNode);
    }

    private void indent() {
        for (int i = 0; i < indentLevel; i++) {
            out.print(indentText);
        }
    }

    protected void printFirstNode(Node node) {
        out.println(node);
    }

    protected void printNode(Node previousNode, Node currentNode) {
        out.print("--> ");
        out.println(currentNode);
    }
}
