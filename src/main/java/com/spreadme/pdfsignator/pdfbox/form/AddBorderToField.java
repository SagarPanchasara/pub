/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.spreadme.pdfsignator.pdfbox.form;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;

/**
 * Add a border to an existing field.
 * 
 * This sample adds a border to a field.
 * 
 * This sample builds on the form generated by @link CreateSimpleForm so you need to run that first.
 * 
 */
public final class AddBorderToField
{
    static final String RESULT_FILENAME = "target/AddBorderToField.pdf";

    private AddBorderToField()
    {
    }
    
    public static void main(String[] args) throws IOException
    {
        // Load the PDF document created by SimpleForm.java
        try (PDDocument document = Loader.loadPDF(new File(CreateSimpleForm.DEFAULT_FILENAME)))
        {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            
            // Get the field and the widget associated to it.
            // Note: there might be multiple widgets
            PDField field = acroForm.getField("SampleField");
            PDAnnotationWidget widget = field.getWidgets().get(0);
            
            // Create the definition for a red border
            PDAppearanceCharacteristicsDictionary fieldAppearance =
                    new PDAppearanceCharacteristicsDictionary(new COSDictionary());
            PDColor red = new PDColor(new float[] { 1, 0, 0 }, PDDeviceRGB.INSTANCE);
            fieldAppearance.setBorderColour(red);

            // Set the information to be used by the widget which is responsible
            // for the visual style of the form field.
            widget.setAppearanceCharacteristics(fieldAppearance);
            
            document.save(RESULT_FILENAME);
        }
    }
}
