using System;


namespace rt
{
    public class Ellipsoid : Geometry
    {
        private Vector Center { get; }
        private Vector SemiAxesLength { get; }
        
        private Vector SemiAxesLengthSquared { get; }
        private double Radius { get; }
        
        
        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Material material, Color color) : base(material, color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            SemiAxesLengthSquared = new Vector(SemiAxesLength.X * SemiAxesLength.X, SemiAxesLength.Y * SemiAxesLength.Y, SemiAxesLength.Z * SemiAxesLength.Z);
            Radius = radius;
        }

        public Ellipsoid(Vector center, Vector semiAxesLength, double radius, Color color) : base(color)
        {
            Center = center;
            SemiAxesLength = semiAxesLength;
            SemiAxesLengthSquared = new Vector(SemiAxesLength.X * SemiAxesLength.X, SemiAxesLength.Y * SemiAxesLength.Y, SemiAxesLength.Z * SemiAxesLength.Z);
            Radius = radius;
        }
        /// <summary>
        /// Scales the components of the input vector by the semi-axes lengths of the ellipsoid.
        /// </summary>
        /// <param name="v">The input vector to be scaled</param>
        /// <returns>A new vector with each component scaled by the corresponding semi-axes length</returns>
        private Vector Linify(Vector v)
        {
            return new Vector(v.X / SemiAxesLength.X, v.Y / SemiAxesLength.Y, v.Z / SemiAxesLength.Z);
        }

        /// <summary>
        /// Scales the components of the input vector by the squared semi-axes lengths of the ellipsoid.
        /// </summary>
        /// <param name="v">The input vector to be scaled.</param>
        /// <returns>A new vector with each component scaled by the corresponding squared semi-axes length.</returns>
        private Vector NormalLinify(Vector v)
        {
            return new Vector(v.X / SemiAxesLengthSquared.X, v.Y / SemiAxesLengthSquared.Y, v.Z / SemiAxesLengthSquared.Z);
        }
        
        /// <summary>
        /// Calculates the first and last intersection points of a line with the ellipsoid.
        /// </summary>
        /// <param name="line">The line to check for intersection.</param>
        /// <returns>A tuple containing the first and last intersection distances, or null if no intersection occurs.</returns>
        public Tuple<double?, double?> GetFirstAndLastIntersection(Line line)
        {
            Vector dtOverE = Linify(line.Dx);
            Vector x0COverE = Linify(line.X0 - Center);
            double a = dtOverE.Length2();
            double b = dtOverE * x0COverE * 2;
            double c = x0COverE.Length2() - Radius * Radius;
            double delta = b * b - 4 * a * c;
            
            if (delta < 1e-10) return new Tuple<double?, double?>(null, null);
            
            if (Math.Abs(delta) < 1e-10)
            {
                return new Tuple<double?, double?>(-b / (2 * a), null);
            }
            
            double sqrtDelta = Math.Sqrt(delta);
            double inverse2A = 1 / (2 * a);
            double t1 = (-b - sqrtDelta) * inverse2A;
            double t2 = (-b + sqrtDelta) * inverse2A;
            return new Tuple<double?, double?>(t1, t2);
        }

        /// <summary>
        /// Calculates the intersection of a line with the ellipsoid.
        /// </summary>
        /// <param name="line">The line to check for intersection.</param>
        /// <param name="minDist">The minimum distance from the line's origin to consider an intersection.</param>
        /// <param name="maxDist">The maximum distance from the line's origin to consider an intersection.</param>
        /// <param name="onlyFirst">If true, only the first intersection is considered.</param>
        /// <returns>An Intersection object representing the intersection details.</returns>
        public override Intersection GetIntersection(Line line, double minDist, double maxDist, bool? onlyFirst)
        {
            // TODO: ADD CODE HERE
            var (t1, t2) = GetFirstAndLastIntersection(line); 
            if (t1 == null)
            {
                return Intersection.NONE;
            }
            if (t2 == null)
            {
                var t = t1 ?? 0;
                var visible = t >= minDist && t <= maxDist;
                var position = line.CoordinateToPosition(t);
                var normal = NormalLinify((position - Center) * 2).Normalize();
                return new Intersection(true, visible, this, line, t, normal, Material, Color);
            }
            else
            {
                var visible = (t1 >= minDist && t1 <= maxDist) || (t2 >= minDist && t2 <= maxDist);
                var t = (t1 >= minDist ? t1 : t2) ?? 0; // closest point
                var position = line.CoordinateToPosition(t);
                var normal = NormalLinify((position - Center) * 2).Normalize();
                return new Intersection(true, visible, this, line, t, normal, Material, Color);
            }
        }
    }
}
