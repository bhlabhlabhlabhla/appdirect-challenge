/**
 * Application is built on very simple architecture. There are 3 layer of services which serves the business:
 *   - Event Processor Service
 *   - Dto To Entity Conversion Service
 *   - Validation Processor Service
 *
 * Each of these service interfaces has been implemented to serve different needs. We can horizontally scale this
 * application to support more integrations by simply adding more implementations into above services.
 */
package com.appdirect.app;