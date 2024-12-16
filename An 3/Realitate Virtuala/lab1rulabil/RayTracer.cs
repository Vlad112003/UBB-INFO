using System;

namespace rt
{
    class RayTracer
    {
        private Geometry[] geometries;
        private Light[] lights;

        
        public RayTracer(Geometry[] geometries, Light[] lights)
        {
            this.geometries = geometries;
            this.lights = lights;
        }

        /// <summary>
        /// Converts an image coordinate to a view plane coordinate.
        /// </summary>
        /// <param name="n">The image coordinate.</param>
        /// <param name="imgSize">The size of the image dimension.</param>
        /// <param name="viewPlaneSize">The size of the view plane dimension.</param>
        /// <returns>The corresponding view plane coordinate.</returns>
        private double ImageToViewPlane(int n, int imgSize, double viewPlaneSize)
        {
            return -n * viewPlaneSize / imgSize + viewPlaneSize / 2;
        }

        /// <summary>
        /// Finds the first intersection of a ray with the geometries in the scene.
        /// </summary>
        /// <param name="ray">The ray to check for intersections.</param>
        /// <param name="minDist">The minimum distance from the ray's origin to consider an intersection.</param>
        /// <param name="maxDist">The maximum distance from the ray's origin to consider an intersection.</param>
        /// <returns>An Intersection object representing the first intersection found.</returns>

        private Intersection FindFirstIntersection(Line ray, double minDist, double maxDist)
        {
            var intersection = Intersection.NONE;

            foreach (var geometry in geometries)
            {
                var intr = geometry.GetIntersection(ray, minDist, maxDist);

                if (!intr.Valid || !intr.Visible) continue;

                if (!intersection.Valid || !intersection.Visible)
                {
                    intersection = intr;
                }
                else if (intr.T < intersection.T)
                {
                    intersection = intr;
                }
            }

            return intersection;
        }

        /// <summary>
        /// Determines if a point is lit by a given light source.
        /// </summary>
        /// <param name="point">The point to check for illumination.</param>
        /// <param name="light">The light source.</param>
        /// <returns>True if the point is lit by the light source, otherwise false.</returns>
        private bool IsLit(Vector point, Light light)
        {
            // TODO: ADD CODE HERE
            var line = new Line(point, light.Position);
            const double eps = 1e-10;
            var segmentLength = (point - light.Position).Length();
            foreach (var geometry in geometries)
            {
                if (geometry is RawCtMask)
                {
                    continue;
                }
                var inter = geometry.GetIntersection(line, eps, segmentLength, true);
                if (inter.Visible)
                {
                    return false;
                }
            }
            return true;
        }

        /// <summary>
        /// Renders the scene from the perspective of the given camera and saves the image to a file.
        /// </summary>
        /// <param name="camera">The camera to render the scene from.</param>
        /// <param name="width">The width of the output image.</param>
        /// <param name="height">The height of the output image.</param>
        /// <param name="filename">The filename to save the rendered image to.</param>
        public void Render(Camera camera, int width, int height, string filename)
        {
            var background = new Color(0.2, 0.2, 0.2, 1.0);
            var viewParallel = (camera.Up ^ camera.Direction).Normalize();

            var image = new Image(width, height);
            var vecW = camera.Direction * camera.ViewPlaneDistance;

            for (var i = 0; i < width; i++)
            {
                var kw = ImageToViewPlane(i, width, camera.ViewPlaneWidth);
                for (var j = 0; j < height; j++)
                {
                    var kh = ImageToViewPlane(j, height, camera.ViewPlaneHeight);
                    var rayVector = camera.Position + vecW + viewParallel * kw + camera.Up * kh;
                    var ray = new Line(camera.Position, rayVector);
                    var inter = FindFirstIntersection(ray, camera.FrontPlaneDistance, camera.BackPlaneDistance);
                    if (!inter.Visible)
                    {
                        image.SetPixel(i, j, background);
                        continue;
                    }
                    var material = inter.Material;
                    var globalColor = new Color();
                    var V = inter.Position;
                    var E = (camera.Position - V).Normalize();
                    var N = inter.Normal;
                    foreach (var light in lights)
                    {
                        var color = material.Ambient * light.Ambient;
                        if (IsLit(V, light))
                        {
                            var T = (light.Position - V).Normalize();
                            var R = (N * (N * T) * 2 - T).Normalize();
                            var diffuseFactor = N * T;
                            var specularFactor = E * R;
                            if (diffuseFactor > 0)
                            {
                                color += material.Diffuse * light.Diffuse * diffuseFactor;
                            }
                            if (specularFactor > 0)
                            {
                                color += material.Specular * light.Specular * Math.Pow(specularFactor, material.Shininess);
                            }
                            color *= light.Intensity;
                        }

                        globalColor += color;
                    }

                    image.SetPixel(i, j, globalColor);
                }
            }

            image.Store(filename);
        }
    }
}