package org.dbpedia.extraction.mappings.rml

import be.ugent.mmlab.rml.model.TriplesMap
import org.dbpedia.extraction.ontology.{Ontology, OntologyClass, OntologyProperty}
import org.openrdf.model.URI
import scala.language.reflectiveCalls

/**
  * Loading ontology functions
  */
object RMLOntologyLoader {

  // "class" is the rml value
  private final val mapToClass: String = "class"

  def loadMapToClassOntology(triplesMap: TriplesMap, context: {def ontology : Ontology}): OntologyClass = {
      loadTriplesMapOntologyClass(triplesMap, mapToClass, context)
  }

  def loadCorrespondingPropertyOntology(triplesMap: TriplesMap, context: {def ontology : Ontology}): OntologyProperty = {
      null //TODO: ? not defined in the rml mappings
  }

  def loadCorrespondingClassOntology(triplesMap: TriplesMap, context: {def ontology : Ontology}): OntologyClass = {
      null //TODO: ? not defined in the rml mappings
  }

  def loadOntologyClass(ontologyClassName : String, context: {def ontology: Ontology}): OntologyClass = {
      try {
        context.ontology.classes(ontologyClassName)
      } catch {
        case _: NoSuchElementException => throw new IllegalArgumentException("Ontology class not found: " + ontologyClassName)
      }

  }

  def loadOntologyProperty(ontologyPropertyName: String, context: {def ontology: Ontology}): OntologyProperty = {
      try {
        context.ontology.properties(ontologyPropertyName)
      } catch {
        case _ : NoSuchElementException => throw new IllegalArgumentException("Ontology property not found: " + ontologyPropertyName)
      }
  }

  def loadOntologyPropertyFromIRI(ontologyIRI : String, context : {def ontology: Ontology}): OntologyProperty = {
      //TODO: change in RMLProcessor for looking up local ontology property
      val localOntologyPropertyName = ontologyIRI.replaceAll(".*/","")
      return loadOntologyProperty(localOntologyPropertyName, context)
  }

  private def loadTriplesMapOntologyClass(triplesMap: TriplesMap, ontologyType : String, context: {def ontology : Ontology}): OntologyClass = {
      val ontologyClassName = loadTriplesMapOntologyClassName(triplesMap)
      return loadOntologyClass(ontologyClassName, context)
  }

  private def loadTriplesMapOntologyClassName(triplesMap: TriplesMap): String = {
      triplesMap.getSubjectMap.getClassIRIs.toArray.head.asInstanceOf[URI].getLocalName
  }


}
